package com.example.bmiassignment.presentation;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.res.Configuration;
import android.os.LocaleList;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.bmiassignment.domain.BmiCategory;
import com.example.bmiassignment.domain.BmiResult;
import com.example.bmiassignment.validation.InputError;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

@RunWith(AndroidJUnit4.class)
public class BmiTextFormatterTest {

    private BmiTextFormatter formatterFor(String languageTag) {
        Context appContext = InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext();

        Configuration config = new Configuration(
                appContext.getResources().getConfiguration()
        );

        config.setLocales(
                new LocaleList(Locale.forLanguageTag(languageTag))
        );

        Context localizedContext =
                appContext.createConfigurationContext(config);

        return new BmiTextFormatter(localizedContext);
    }

    @Test
    public void format_english_showsValueAndCategory() {
        BmiTextFormatter formatter = formatterFor("en-US");

        ResultText text = formatter.format(
                new BmiResult(23.030045, BmiCategory.NORMAL)
        );

        assertEquals("23.03 kg/m²", text.valueText);
        assertEquals("Normal", text.categoryText);
    }

    @Test
    public void format_thai_showsValueAndCategory() {
        BmiTextFormatter formatter = formatterFor("th-TH");

        ResultText text = formatter.format(
                new BmiResult(23.030045, BmiCategory.NORMAL)
        );

        assertEquals("23.03 กก./ม.²", text.valueText);
        assertEquals("ปกติ", text.categoryText);
    }

    @Test
    public void format_keepsTwoDecimalPlaces() {
        BmiTextFormatter formatter = formatterFor("en-US");

        ResultText text = formatter.format(
                new BmiResult(25.0, BmiCategory.OVERWEIGHT)
        );

        assertEquals("25.00 kg/m²", text.valueText);
    }

    @Test
    public void format_roundsHalfUp() {
        BmiTextFormatter formatter = formatterFor("en-US");

        ResultText text = formatter.format(
                new BmiResult(22.865, BmiCategory.NORMAL)
        );

        assertEquals("22.87 kg/m²", text.valueText);
    }

    @Test
    public void format_doesNotReclassifyRoundedValue() {
        BmiTextFormatter formatter = formatterFor("en-US");

        ResultText text = formatter.format(
                new BmiResult(24.999, BmiCategory.NORMAL)
        );

        assertEquals("25.00 kg/m²", text.valueText);
        assertEquals("Normal", text.categoryText);
    }

    @Test
    public void format_doesNotUseThousandsSeparator() {
        BmiTextFormatter formatter = formatterFor("en-US");

        ResultText text = formatter.format(
                new BmiResult(1234.5, BmiCategory.OBESE_III)
        );

        assertEquals("1234.50 kg/m²", text.valueText);
    }

    @Test
    public void format_usesCurrentResourceLocale() {
        BmiTextFormatter formatter = formatterFor("de-DE");

        ResultText text = formatter.format(
                new BmiResult(23.03, BmiCategory.NORMAL)
        );

        // German uses a decimal comma.
        // Text falls back to the default English resources.
        assertEquals("23,03 kg/m²", text.valueText);
        assertEquals("Normal", text.categoryText);
    }

    @Test
    public void format_translatesEveryCategory() {
        BmiCategory[] categories = {
                BmiCategory.SEVERE_THINNESS,
                BmiCategory.MODERATE_THINNESS,
                BmiCategory.MILD_THINNESS,
                BmiCategory.NORMAL,
                BmiCategory.OVERWEIGHT,
                BmiCategory.OBESE_I,
                BmiCategory.OBESE_II,
                BmiCategory.OBESE_III
        };

        double[] bmiValues = {
                15.0, 16.5, 18.0, 23.0,
                27.0, 32.0, 37.0, 42.0
        };

        String[] english = {
                "Severe thinness",
                "Moderate thinness",
                "Mild thinness",
                "Normal",
                "Overweight",
                "Obese class I",
                "Obese class II",
                "Obese class III"
        };

        String[] thai = {
                "ผอมมาก",
                "ผอมปานกลาง",
                "ผอมเล็กน้อย",
                "ปกติ",
                "น้ำหนักเกิน",
                "อ้วนระดับ 1",
                "อ้วนระดับ 2",
                "อ้วนระดับ 3"
        };

        BmiTextFormatter enFormatter = formatterFor("en-US");
        BmiTextFormatter thFormatter = formatterFor("th-TH");

        for (int i = 0; i < categories.length; i++) {
            BmiResult result = new BmiResult(
                    bmiValues[i], categories[i]
            );

            assertEquals(
                    categories[i].name(),
                    english[i],
                    enFormatter.format(result).categoryText
            );

            assertEquals(
                    categories[i].name(),
                    thai[i],
                    thFormatter.format(result).categoryText
            );
        }
    }

    @Test
    public void inputError_english_coversEveryError() {
        BmiTextFormatter formatter = formatterFor("en-US");

        assertEquals("", formatter.inputError(InputError.NONE));
        assertEquals(
                "Please enter a value.",
                formatter.inputError(InputError.REQUIRED)
        );
        assertEquals(
                "Enter a number using a decimal point.",
                formatter.inputError(InputError.INVALID_NUMBER)
        );
        assertEquals(
                "Enter a value greater than zero.",
                formatter.inputError(InputError.NON_POSITIVE)
        );
    }

    @Test
    public void inputError_thai_coversEveryError() {
        BmiTextFormatter formatter = formatterFor("th-TH");

        assertEquals("", formatter.inputError(InputError.NONE));
        assertEquals(
                "กรุณากรอกข้อมูล",
                formatter.inputError(InputError.REQUIRED)
        );
        assertEquals(
                "กรุณากรอกตัวเลข โดยใช้จุดสำหรับทศนิยม",
                formatter.inputError(InputError.INVALID_NUMBER)
        );
        assertEquals(
                "กรุณากรอกค่าที่มากกว่า 0",
                formatter.inputError(InputError.NON_POSITIVE)
        );
    }

    @Test
    public void messages_english() {
        BmiTextFormatter formatter = formatterFor("en-US");

        /*
         * ข้อความที่ Test คาดหวังต้องตรงกับ String Resource
         * ที่แสดงบนปุ่มและ Result Card เวอร์ชันปัจจุบัน
         */
        assertEquals(
                "Enter weight and height, then tap Calculate BMI.",
                formatter.emptyMessage()
        );
        assertEquals(
                "Unable to calculate. Check the values entered.",
                formatter.calculationErrorMessage()
        );
    }


    @Test
    public void messages_thai() {
        BmiTextFormatter formatter = formatterFor("th-TH");

        /*
         * ตรวจว่าข้อความภาษาไทยใช้คำว่า BMI
         * ตรงกับปุ่มคำนวณและข้อความแนะนำบนหน้าจอ
         */
        assertEquals(
                "กรอกน้ำหนักและส่วนสูง แล้วกดคำนวณ BMI",
                formatter.emptyMessage()
        );
        assertEquals(
                "ไม่สามารถคำนวณได้ กรุณาตรวจสอบค่าที่กรอก",
                formatter.calculationErrorMessage()
        );
    }
}