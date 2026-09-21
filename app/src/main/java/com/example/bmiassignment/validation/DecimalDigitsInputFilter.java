package com.example.bmiassignment.validation;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

/**
 * จำกัดจำนวนหลักของตัวเลขทศนิยมที่ผู้ใช้กรอก
 *
 * ระหว่างที่ผู้ใช้กำลังพิมพ์ จะอนุญาตสถานะชั่วคราว เช่น
 * ข้อความว่าง เครื่องหมายลบ หรือจุดทศนิยม
 *
 * InputValidator จะตรวจความถูกต้องของค่าที่กรอกเสร็จแล้ว
 * เมื่อผู้ใช้กดปุ่มคำนวณอีกครั้ง
 */
public final class DecimalDigitsInputFilter implements InputFilter {

    private final Pattern acceptedPattern;

    /**
     * สร้างตัวกรองสำหรับตัวเลขทศนิยม
     *
     * @param maxIntegerDigits จำนวนหลักสูงสุดก่อนจุดทศนิยม
     * @param maxFractionDigits จำนวนหลักสูงสุดหลังจุดทศนิยม
     */
    public DecimalDigitsInputFilter(
            int maxIntegerDigits,
            int maxFractionDigits
    ) {
        /*
         * ป้องกันการสร้างตัวกรองด้วยค่าที่ไม่สมเหตุสมผล
         * จำนวนหลักของจำนวนเต็มต้องมีอย่างน้อย 1 หลัก
         * และจำนวนทศนิยมต้องไม่ติดลบ
         */
        if (maxIntegerDigits < 1 || maxFractionDigits < 0) {
            throw new IllegalArgumentException(
                    "Invalid digit limits"
            );
        }

        /*
         * รูปแบบข้อมูลที่อนุญาต:
         * - มีเครื่องหมายบวกหรือลบด้านหน้าหรือไม่มีก็ได้
         * - จำนวนหลักก่อนจุดไม่เกิน maxIntegerDigits
         * - มีจุดทศนิยมหรือไม่มีก็ได้
         * - จำนวนหลักหลังจุดไม่เกิน maxFractionDigits
         *
         * ตัวอย่างเมื่อกำหนดค่าเป็น 8 และ 2:
         * 65, 65.5, 65.50, .5 และ -5.25
         */
        acceptedPattern = Pattern.compile(
                "^[+-]?(?:[0-9]{0," + maxIntegerDigits + "}" +
                        "(?:\\.[0-9]{0," + maxFractionDigits + "})?" +
                        "|\\.[0-9]{0," + maxFractionDigits + "})$"
        );
    }

    @Override
    public CharSequence filter(
            CharSequence source,
            int start,
            int end,
            Spanned destination,
            int destinationStart,
            int destinationEnd
    ) {
        /*
         * สร้างข้อความที่ควรจะเป็นหลังจาก Android นำข้อความใหม่
         * ไปแทรก แทนที่ หรือลบออกจากข้อความเดิม
         */
        String candidate =
                destination.subSequence(0, destinationStart)
                        + source.subSequence(start, end).toString()
                        + destination.subSequence(
                        destinationEnd,
                        destination.length()
                );

        if (acceptedPattern.matcher(candidate).matches()) {
            /*
             * คืนค่า null เพื่อบอก Android ว่า
             * สามารถยอมรับการเปลี่ยนแปลงครั้งนี้ได้
             */
            return null;
        }

        /*
         * คืนข้อความว่างเพื่อปฏิเสธตัวอักษรใหม่
         * เมื่อข้อมูลเกินจำนวนหลักที่กำหนด
         */
        return "";
    }
}