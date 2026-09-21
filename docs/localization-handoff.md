# ส่วน E — ภาษาและรูปแบบข้อความ

## 1 — ข้อความสองภาษา

เตรียมข้อความภาษาอังกฤษและภาษาไทยสำหรับแอป BMI รวม **23 keys ต่อภาษา** โดยใช้ชื่อ key ตรงกันทั้งสองไฟล์

| ไฟล์ | หน้าที่ |
| --- | --- |
| `app/src/main/res/values/strings.xml` | ข้อความภาษาอังกฤษและข้อความสำรองสำหรับภาษาที่ไม่ได้รองรับ |
| `app/src/main/res/values-th/strings.xml` | ข้อความภาษาไทย |

ข้อความครอบคลุมชื่อแอป คำอธิบายขอบเขตผู้ใช้ ป้ายช่องกรอก ปุ่มคำนวณ ผลลัพธ์ หน่วย ข้อความผิดพลาด และเกณฑ์ BMI ทั้ง 8 แบบ

### การนำไปใช้

ใน XML ให้เรียกผ่าน `@string/ชื่อ_key` เช่น:

```xml
android:text="@string/action_calculate"
```

ใน Java ให้เรียกผ่าน resources เช่น:

```java
context.getString(R.string.action_calculate);
```

A, C และ D สามารถใช้ key ที่เตรียมไว้กับหน้าจอของตนได้ โดยไม่เขียนข้อความไทยหรืออังกฤษตรง ๆ ใน Java หรือ Layout หากต้องเพิ่มข้อความ ให้ประสาน E เพื่อเพิ่มทั้งสองภาษาให้ครบ

### สถานะ

ตรวจรูปแบบ XML และชื่อ key ทั้งสองภาษาแล้ว ยังต้องตรวจข้อความบนหน้าจอจริงและการจัดวางหลังรวมแอป

## 2 — จัดรูปแบบผลลัพธ์

เตรียมคลาสสำหรับแปลงผลคำนวณและรหัสข้อผิดพลาดเป็นข้อความตามภาษาของ Context ปัจจุบัน

ไฟล์ Java ทั้งสองอยู่ใต้ `app/src/main/java/com/example/bmiassignment/presentation/`

| ไฟล์ | หน้าที่ |
| --- | --- |
| `ResultText.java` | เก็บ `valueText` สำหรับค่า BMI พร้อมหน่วย และ `categoryText` สำหรับข้อความเกณฑ์ |
| `BmiTextFormatter.java` | จัดรูปแบบตัวเลข เลือกข้อความเกณฑ์ และสร้างข้อความ error หรือสถานะเริ่มต้น |

### เมธอดสำหรับนำไปใช้

| เมธอด | ผลลัพธ์ |
| --- | --- |
| `format(BmiResult result)` | คืน `ResultText` ที่พร้อมนำไปแสดง |
| `inputError(InputError error)` | คืนข้อความ error โดย `NONE` คืนสตริงว่าง |
| `emptyMessage()` | คืนข้อความแนะนำก่อนคำนวณ |
| `calculationErrorMessage()` | คืนข้อความเมื่อคำนวณไม่สำเร็จ |

ตัวอย่างสำหรับ A ภายใน Activity เมื่อมี `BmiResult result` และ `resultBinder` แล้ว:

```java
BmiTextFormatter formatter = new BmiTextFormatter(this);
ResultText text = formatter.format(result);

resultBinder.showResult(result, text);
```

C เรียก `formatter.inputError(error)` เพื่อรับข้อความผิดพลาด สำหรับ `InputError.NONE` ให้ล้าง error ของ View

### กติกาการจัดรูปแบบ

- แสดงทศนิยม 2 ตำแหน่ง ปัดแบบ `HALF_UP` และไม่มีตัวคั่นหลักพัน
- ใช้ locale จาก resources ปัจจุบัน แล้วเติมหน่วยผ่าน `bmi_value_format`
- แปลเกณฑ์จาก `BmiCategory` ที่ B ส่งมา ไม่คำนวณหรือตัดเกณฑ์ใหม่ เช่น `24.999` ที่เป็น `NORMAL` จะแสดง `25.00` และ “ปกติ” เมื่อใช้ภาษาไทย
- A ต้องสร้าง formatter ใหม่จาก Activity ปัจจุบันเมื่อ Activity ถูกสร้างใหม่ ไม่เก็บ formatter เป็น `static` หรือบันทึกข้อความแปลแล้วลงสถานะ

### สิ่งที่ต้องมีจากเพื่อน

ตำแหน่งต่อไปนี้อยู่ใต้ `app/src/main/java/com/example/bmiassignment/`

| ไฟล์ | เจ้าของ | ข้อกำหนดที่ E ใช้ |
| --- | --- | --- |
| `domain/BmiResult.java` | B | `public final double bmi` และ `public final BmiCategory category` พร้อม constructor ตามลำดับนี้ |
| `domain/BmiCategory.java` | B | enum เกณฑ์ทั้ง 8 แบบตามเอกสารกลาง |
| `validation/InputError.java` | C | enum `NONE`, `REQUIRED`, `INVALID_NUMBER`, `NON_POSITIVE` |

### สถานะและการตรวจตอนรวมงาน

ยังไม่ได้ build และรันทดสอบร่วมกับคลาสของเพื่อน โค้ดปัจจุบันใช้ API ที่ต้องการ Minimum SDK 24 จึงต้องตรวจให้ตรงกับโครงงานกลางของ A ก่อนรวมงาน

ก่อนส่งงาน ให้ตรวจว่า `ResultText.java` มี implementation ครบ เพราะไฟล์นี้ใน ZIP ที่ตรวจครั้งแรกยังว่างอยู่ หลังได้คลาส B/C ครบแล้วจึง build และรันชุดทดสอบ รวมถึงตรวจการเปลี่ยนภาษาระบบจริงและ Layout ทั้งสองภาษา

## 3 — เตรียมทดสอบ

เตรียม automated tests สำหรับตรวจข้อความและรูปแบบตัวเลขของส่วน E ในไฟล์:

`app/src/androidTest/java/com/example/bmiassignment/presentation/BmiTextFormatterTest.java`

เป็น Android instrumented test ใช้ `AndroidJUnit4` และต้องรันบน Emulator หรืออุปกรณ์ Android โดยสร้าง Context แยกตาม locale เพื่อทดสอบ formatter ไม่เปลี่ยนภาษาระบบจริงและไม่เรียกสูตรคำนวณของ B ข้อมูล `BmiResult` ตัวอย่างถูกสร้างเฉพาะใน test

### ขอบเขตที่เตรียมทดสอบ

ในไฟล์มี 12 test methods ครอบคลุมกรณีต่อไปนี้:

| กรณี | ผลที่คาดหวัง |
| --- | --- |
| ค่าและเกณฑ์ภาษาอังกฤษ | `23.030045` กับ `NORMAL` แสดง `23.03 kg/m²` และ `Normal` |
| ค่าและเกณฑ์ภาษาไทย | แสดง `23.03 กก./ม.²` และ `ปกติ` |
| จำนวนเต็ม | `25.0` แสดง `25.00 kg/m²` |
| การปัด HALF_UP | `22.865` แสดง `22.87 kg/m²` |
| ไม่จัดเกณฑ์ใหม่หลังปัด | `24.999` กับ `NORMAL` แสดง `25.00 kg/m²` และยังเป็น `Normal` |
| ไม่ใส่ตัวคั่นหลักพัน | `1234.5` แสดง `1234.50 kg/m²` เป็นข้อมูลสังเคราะห์เพื่อทดสอบรูปแบบเท่านั้น |
| locale ที่ไม่มีคำแปลเฉพาะ | `de-DE` ใช้ comma เป็นทศนิยม เช่น `23,03 kg/m²` และใช้ข้อความอังกฤษสำรอง |
| เกณฑ์ BMI ทุก enum | คำแปลถูกต้องทั้ง 8 เกณฑ์ ทั้งไทยและอังกฤษ |
| InputError ทุก enum | ตรวจทั้งสองภาษา รวม `NONE` ที่ต้องคืนสตริงว่าง |
| ข้อความสถานะ | ข้อความก่อนคำนวณและคำนวณไม่สำเร็จถูกต้องทั้งสองภาษา |

ข้อความที่เขียนตรง ๆ ใน test เป็นค่าคาดหวังสำหรับตรวจเทียบ ไม่ใช่ข้อความ UI ในโค้ดแอป

### สิ่งที่ต้องพร้อมก่อนรัน

- B ส่ง `BmiResult` และ `BmiCategory` ตามสัญญากลาง และ C ส่ง `InputError` แล้ว
- `ResultText.java` มี implementation ครบ และ string resources ทั้งสองภาษาพร้อม
- A ตรวจ Gradle ให้มี dependencies ของ AndroidX Test/JUnit และ instrumented test runner ที่เหมาะสม โดย E ไม่เปลี่ยนเวอร์ชัน build แยกจากทีม
- โครงงาน build ผ่าน และอุปกรณ์ทดสอบรองรับ API ที่โค้ดใช้ (ปัจจุบัน API 24 ขึ้นไป)

### วิธีรัน

1. เปิดโปรเจกต์กลางใน Android Studio และรอ Gradle Sync เสร็จ
2. เปิด Emulator หรือเชื่อมต่ออุปกรณ์ Android สำหรับทดสอบ
3. เปิด `BmiTextFormatterTest.java` ใต้ `androidTest`
4. รัน test ทั้งคลาสจากปุ่ม Run ข้างชื่อคลาส หรือเมนู Run ของไฟล์ แล้วเลือกอุปกรณ์
5. บันทึกจำนวน tests ที่ผ่าน/ไม่ผ่าน พร้อมข้อความ failure หากมี
