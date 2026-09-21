# ภาษาและการจัดรูปแบบผลลัพธ์ BMI

เอกสารนี้อธิบายการรองรับภาษาอังกฤษ–ภาษาไทย และการจัดรูปแบบผลลัพธ์ของแอป BMI หลังรวมงานทุกส่วนแล้ว

## 1. String Resources

แอปมีข้อความจำนวน **26 keys ต่อภาษา** โดยใช้ชื่อ key ตรงกันทั้งสองไฟล์:

| ไฟล์ | หน้าที่ |
|---|---|
| `app/src/main/res/values/strings.xml` | ภาษาอังกฤษและข้อความเริ่มต้นสำหรับภาษาที่ไม่มีคำแปล |
| `app/src/main/res/values-th/strings.xml` | ภาษาไทย |

ข้อความครอบคลุม:

- ชื่อแอปและคำอธิบายขอบเขตผู้ใช้
- หัวข้อและคำแนะนำของแบบฟอร์ม
- ป้ายและ Hint ของช่องน้ำหนักกับส่วนสูง
- ปุ่มคำนวณ
- หัวข้อและข้อความในส่วนผลลัพธ์
- ข้อความตรวจสอบข้อมูล
- รูปแบบค่า BMI พร้อมหน่วย
- ชื่อหมวด BMI ทั้ง 8 หมวด

ใน XML เรียกข้อความผ่าน String Resource เช่น:

```xml
android:text="@string/action_calculate"
```

ใน Java เรียกผ่าน `Context` เช่น:

```java
context.getString(R.string.action_calculate);
```

ข้อความที่ผู้ใช้มองเห็นจึงไม่ได้เขียนตรงไว้ใน Java หรือ Layout และ Android จะเลือกภาษาให้ตามภาษาของอุปกรณ์โดยอัตโนมัติ

## 2. คลาสจัดรูปแบบข้อความ

ไฟล์ที่เกี่ยวข้องอยู่ใน:

```text
app/src/main/java/com/example/bmiassignment/presentation/
```

| ไฟล์ | หน้าที่ |
|---|---|
| `ResultText.java` | เก็บข้อความค่า BMI และชื่อหมวดที่พร้อมแสดง |
| `BmiTextFormatter.java` | จัดรูปแบบตัวเลข หน่วย หมวด BMI และข้อความผิดพลาดตามภาษา |

### Public API

| เมธอด | ผลลัพธ์ |
|---|---|
| `format(BmiResult result)` | คืน `ResultText` ที่มีค่า BMI พร้อมหน่วยและชื่อหมวด |
| `inputError(InputError error)` | คืนข้อความตรวจสอบข้อมูล โดย `NONE` คืนข้อความว่าง |
| `emptyMessage()` | คืนคำแนะนำก่อนคำนวณ |
| `calculationErrorMessage()` | คืนข้อความเมื่อคำนวณไม่สำเร็จ |

ตัวอย่างการใช้งาน:

```java
BmiTextFormatter formatter = new BmiTextFormatter(this);
ResultText text = formatter.format(result);
resultBinder.showResult(result, text);
```

## 3. กติกาการจัดรูปแบบ

- แสดงค่า BMI ด้วยทศนิยม 2 ตำแหน่ง
- ปัดเศษด้วย `RoundingMode.HALF_UP`
- ไม่แสดงตัวคั่นหลักพัน
- ใช้ Locale จาก Resources ของ Activity ปัจจุบัน
- ใช้ `bmi_value_format` เพื่อเติมหน่วยตามภาษา
- ใช้ค่า BMI จริงก่อนปัดเศษในการจำแนกหมวด
- แปลง `BmiCategory` เป็นข้อความด้วย String Resource
- สร้างข้อความใหม่เมื่อ Activity ถูกสร้างใหม่ เพื่อให้ตรงกับภาษาปัจจุบัน

ตัวอย่าง:

```text
น้ำหนัก: 65 kg
ส่วนสูง: 168 cm
BMI จริง: 23.030045...
ค่าที่แสดงภาษาอังกฤษ: 23.03 kg/m²
ค่าที่แสดงภาษาไทย: 23.03 กก./ม.²
หมวด: Normal / ปกติ
```

## 4. หมวด BMI ที่รองรับ

| ค่า BMI | English | ภาษาไทย |
|---:|---|---|
| `< 16.0` | Severe thinness | ผอมมาก |
| `16.0 - < 17.0` | Moderate thinness | ผอมปานกลาง |
| `17.0 - < 18.5` | Mild thinness | ผอมเล็กน้อย |
| `18.5 - < 25.0` | Normal | ปกติ |
| `25.0 - < 30.0` | Overweight | น้ำหนักเกิน |
| `30.0 - < 35.0` | Obese class I | อ้วนระดับ 1 |
| `35.0 - < 40.0` | Obese class II | อ้วนระดับ 2 |
| `≥ 40.0` | Obese class III | อ้วนระดับ 3 |

## 5. การเชื่อมกับส่วนอื่นของแอป

ลำดับการทำงานหลังรวมระบบ:

1. `FormBinder` อ่านข้อมูลน้ำหนักและส่วนสูง
2. `InputValidator` ตรวจรูปแบบและค่าที่กรอก
3. `BmiCalculator` คำนวณและจำแนก `BmiCategory`
4. `BmiTextFormatter` สร้างข้อความตามภาษาปัจจุบัน
5. `ResultBinder` แสดงค่า BMI หมวด และสีประกอบ
6. `UiStateStore` เก็บข้อมูลเพื่อคืนสถานะเมื่อ Activity ถูกสร้างใหม่

ระบบรองรับภาษาอังกฤษและภาษาไทยทั้งในสถานะเริ่มต้น ผลลัพธ์สำเร็จ และข้อความผิดพลาด

## 6. Automated Tests

ไฟล์ทดสอบหลักของส่วนนี้:

```text
app/src/androidTest/java/com/example/bmiassignment/presentation/BmiTextFormatterTest.java
```

มี 12 test methods ครอบคลุม:

- ค่าและหมวดภาษาอังกฤษ
- ค่าและหมวดภาษาไทย
- การแสดงทศนิยม 2 ตำแหน่ง
- การปัดแบบ `HALF_UP`
- การคงหมวดเดิมหลังจัดรูปแบบตัวเลข
- การไม่ใช้ตัวคั่นหลักพัน
- การใช้ Locale จาก Resources
- ชื่อหมวด BMI ทั้ง 8 หมวด
- `InputError` ทุกสถานะทั้งสองภาษา
- ข้อความเริ่มต้นและข้อความคำนวณผิดพลาดทั้งสองภาษา

ข้อความที่เขียนตรงใน Test เป็น Expected Value สำหรับตรวจผล ไม่ใช่ข้อความ UI ของแอป

## 7. วิธีตรวจสอบ

Compile ชุด Android Tests:

```bash
./gradlew assembleDebugAndroidTest
```

รัน Instrumented Tests โดยเปิด Emulator หรือเชื่อมต่ออุปกรณ์ก่อน:

```bash
./gradlew connectedDebugAndroidTest
```

ผลการตรวจล่าสุดบน Pixel 6a AVD, Android 15, API 35:

- Android-test APK Compile สำเร็จ
- Instrumented Tests ผ่าน `34/34`
- ภาษาอังกฤษและภาษาไทยแสดงถูกต้อง
- ค่า BMI แสดงทศนิยม 2 ตำแหน่ง
- หมวด BMI แสดงตรงกับผลคำนวณ
- การเปลี่ยนภาษาและการสร้าง Activity ใหม่แสดงข้อความตามภาษาปัจจุบัน

## 8. สถานะปัจจุบัน

- `ResultText` มี implementation ครบ
- Dependency จากสมาชิก B และ C รวมใน `main` แล้ว
- Localization เชื่อมกับ `MainActivity` และ `ResultBinder` แล้ว
- Minimum SDK คือ API 28
- Build, Unit Tests, Android-test Compile และ Instrumented Tests ผ่าน
- ไม่มีข้อความ UI ภาษาอังกฤษหรือภาษาไทย Hard-code ใน Java หรือ Layout