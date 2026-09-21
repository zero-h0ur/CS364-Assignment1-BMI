package com.example.bmiassignment.validation;

import com.example.bmiassignment.domain.BmiInput;

import java.util.regex.Pattern;

public final class InputValidator {

    /*
     * ข้อกำหนดจากเอกสารของอาจารย์:
     * - จำนวนเต็มก่อนจุดทศนิยมไม่เกิน 8 หลัก
     * - ตัวเลขหลังจุดทศนิยมไม่เกิน 2 หลัก
     *
     * กำหนดเป็นค่าคงที่เพื่อให้ FormBinder และ Test
     * ใช้ข้อกำหนดเดียวกันทั้งหมด
     */
    public static final int MAX_INTEGER_DIGITS = 8;
    public static final int MAX_FRACTION_DIGITS = 2;

    /*
     * รูปแบบตัวเลขที่ยอมรับเมื่อผู้ใช้กรอกข้อมูลเสร็จแล้ว
     *
     * ตัวอย่างที่ผ่าน:
     * 65
     * 65.5
     * 65.50
     * .5
     * +65
     * -5.25
     *
     * ตัวอย่างที่ไม่ผ่าน:
     * 123456789
     * 65.123
     * 65,5
     * 1.68e2
     */
    private static final Pattern DECIMAL_PATTERN = Pattern.compile(
            "^[+-]?(?:[0-9]{1," + MAX_INTEGER_DIGITS + "}" +
                    "(?:\\.[0-9]{0," + MAX_FRACTION_DIGITS + "})?" +
                    "|\\.[0-9]{1," + MAX_FRACTION_DIGITS + "})$"
    );

    public static InputValidationResult validate(
            String weightText,
            String heightText
    ) {
        /*
         * ตรวจน้ำหนักและส่วนสูงแยกกัน
         * เพื่อให้สามารถแสดง Error ในช่องที่ผิดได้อย่างถูกต้อง
         */
        InputError weightError = validateField(weightText);
        InputError heightError = validateField(heightText);

        BmiInput input = null;

        /*
         * สร้าง BmiInput เฉพาะเมื่อทั้งสองช่องผ่าน Validation
         * เพื่อป้องกันไม่ให้ข้อมูลที่ผิดถูกส่งไปคำนวณ
         */
        if (weightError == InputError.NONE
                && heightError == InputError.NONE) {
            double weight = Double.parseDouble(weightText.trim());
            double height = Double.parseDouble(heightText.trim());

            input = new BmiInput(weight, height);
        }

        return new InputValidationResult(
                input,
                weightError,
                heightError
        );
    }

    private static InputError validateField(String text) {
        /*
         * null หมายถึงไม่มีข้อมูลถูกส่งเข้ามา
         */
        if (text == null) {
            return InputError.REQUIRED;
        }

        /*
         * ลบช่องว่างหัวและท้ายก่อนตรวจ
         * เพื่อให้ข้อความที่มีแต่ช่องว่างถือว่าไม่ได้กรอกข้อมูล
         */
        String trimmed = text.trim();

        if (trimmed.isEmpty()) {
            return InputError.REQUIRED;
        }

        /*
         * ตรวจรูปแบบและจำนวนหลักก่อนแปลงเป็น double
         * การตรวจนี้ช่วยปฏิเสธทศนิยมเกิน 2 ตำแหน่ง
         * และจำนวนเต็มที่ยาวเกิน 8 หลัก
         */
        if (!DECIMAL_PATTERN.matcher(trimmed).matches()) {
            return InputError.INVALID_NUMBER;
        }

        try {
            double value = Double.parseDouble(trimmed);

            /*
             * ปฏิเสธ NaN และ Infinity เพื่อไม่ให้ค่าที่ไม่ใช่
             * จำนวนจริงถูกส่งไปยังตัวคำนวณ BMI
             */
            if (Double.isNaN(value) || Double.isInfinite(value)) {
                return InputError.INVALID_NUMBER;
            }

            /*
             * น้ำหนักและส่วนสูงต้องมากกว่าศูนย์
             */
            if (value <= 0) {
                return InputError.NON_POSITIVE;
            }
        } catch (NumberFormatException exception) {
            return InputError.INVALID_NUMBER;
        }

        return InputError.NONE;
    }
}