# หน้าที่และผลงานของทีมพัฒนาแอป BMI

เอกสารนี้สรุปการแบ่งงานของสมาชิก 5 คน โครงสร้างการเชื่อมต่อ และผลการตรวจสอบหลังรวมงานทุกส่วนแล้ว

## 1. ขอบเขตของแอป

แอปพัฒนาด้วย Java และ XML Views ภายใต้ package:

```text
com.example.bmiassignment
```

ความสามารถหลัก:

- รับน้ำหนักเป็นกิโลกรัม
- รับส่วนสูงเป็นเซนติเมตร
- ตรวจรูปแบบและค่าที่กรอก
- จำกัดจำนวนเต็มไม่เกิน 8 หลัก
- จำกัดทศนิยมไม่เกิน 2 ตำแหน่ง
- คำนวณ BMI ด้วยสูตรมาตรฐาน
- จำแนกหมวด BMI สำหรับผู้ใหญ่อายุ 20 ปีขึ้นไป
- แสดงผลด้วยทศนิยม 2 ตำแหน่ง
- แสดงชื่อหมวดและสีประกอบ
- รองรับภาษาอังกฤษและภาษาไทย
- รองรับแนวตั้ง แนวนอน และหน้าจอกว้าง
- รองรับ Light mode, Dark mode และ Font size ของอุปกรณ์
- รักษาข้อมูลเมื่อ Activity ถูกสร้างใหม่

## 2. สมาชิกและงานที่รับผิดชอบ

| สมาชิก | ชื่อ | รหัสนักศึกษา | งานหลัก |
|---|---|---:|---|
| A | ปพนพัชร์ มีวน | 6709650458 | Result UI, project baseline และ final integration |
| B | รณกฤต วรลักษณ์ภักดี | 6709650607 | BMI core calculation และ boundary tests |
| C | ภูริณัฐ วรรธนะธัญญา | 6709650565 | Input form และ validation |
| D | ภูรินทร์ แก้วพ่วงเสก | 6709650573 | Responsive layout และ state restoration |
| E | ชนกานต์ คงรัชตภิญโญ | 6709650235 | Thai/English localization และ result formatting |

Pull Requests หลักที่ใช้รวมงาน:

| งาน | Pull Request |
|---|---:|
| Project baseline | #11 |
| Result UI และ integration | #12 |
| BMI core | #14 |
| Input form และ validation | #13 |
| Responsive layout และ state restoration | #10 |
| Localization และ result formatting | #9 |

## 3. สมาชิก A — Result UI และ Integration

### งานที่พัฒนา

- สร้างส่วนแสดงค่า BMI และชื่อหมวด
- แสดงสีตาม `BmiCategory`
- รองรับสถานะเริ่มต้น สำเร็จ และผิดพลาด
- เชื่อม Form, Validation, Calculator, Formatter และ State
- ล้างผลเก่าเมื่อผู้ใช้แก้ข้อมูล
- รองรับการคำนวณซ้ำด้วยข้อมูลล่าสุด
- เชื่อมการบันทึกและคืนสถานะของ Activity
- ดูแลโครงงานตั้งต้น Manifest และ Gradle

### ไฟล์สำคัญ

```text
app/src/main/java/com/example/bmiassignment/MainActivity.java
app/src/main/java/com/example/bmiassignment/ui/ResultBinder.java
app/src/main/res/layout/view_bmi_result.xml
app/src/main/res/values/colors_result.xml
app/src/androidTest/java/com/example/bmiassignment/MainActivityTest.java
app/src/androidTest/java/com/example/bmiassignment/ui/ResultBinderTest.java
```

### การตรวจสอบ

- สถานะเริ่มต้นและผิดพลาด
- ค่า BMI และสีครบทุกหมวด
- การคำนวณครั้งแรกและการคำนวณซ้ำ
- การล้างผลเมื่อข้อมูลเปลี่ยน
- เส้นทางการทำงานตั้งแต่กรอกข้อมูลจนแสดงผล

## 4. สมาชิก B — BMI Core

### งานที่พัฒนา

- สร้าง `BmiInput`
- สร้าง `BmiResult`
- สร้าง enum `BmiCategory`
- คำนวณ BMI จากน้ำหนักและส่วนสูง
- จำแนกหมวดจากค่าจริงก่อนปัดเศษ
- ปฏิเสธ input และผลคำนวณที่ไม่ถูกต้อง
- ทดสอบค่าตรงขอบเขตของทุกหมวด

### ไฟล์สำคัญ

```text
app/src/main/java/com/example/bmiassignment/domain/BmiInput.java
app/src/main/java/com/example/bmiassignment/domain/BmiResult.java
app/src/main/java/com/example/bmiassignment/domain/BmiCategory.java
app/src/main/java/com/example/bmiassignment/domain/BmiCalculator.java
app/src/test/java/com/example/bmiassignment/domain/BmiCalculatorTest.java
```

### สูตร

```text
heightMeters = heightCm / 100
BMI = weightKg / (heightMeters × heightMeters)
```

ตัวอย่าง `65 kg / 168 cm` ได้ค่าประมาณ `23.030045` และแสดงเป็น `23.03`

## 5. สมาชิก C — Input Form และ Validation

### งานที่พัฒนา

- สร้างช่องน้ำหนัก ช่องส่วนสูง และปุ่มคำนวณ
- อ่านข้อมูลจากฟอร์มผ่าน `FormBinder`
- ตรวจช่องว่าง รูปแบบตัวเลข ศูนย์ และค่าติดลบ
- คืน Error แยกสำหรับน้ำหนักและส่วนสูง
- เก็บและคืนข้อมูลผ่าน `FormSnapshot`
- ป้องกัน callback ที่ไม่ต้องการระหว่าง Restore
- เพิ่มตัวกรองจำนวนเต็มไม่เกิน 8 หลักและทศนิยมไม่เกิน 2 ตำแหน่ง
- ตรวจข้อจำกัดอีกครั้งใน Validator เพื่อป้องกันข้อมูลจากภายนอก UI

### ไฟล์สำคัญ

```text
app/src/main/java/com/example/bmiassignment/model/FormSnapshot.java
app/src/main/java/com/example/bmiassignment/ui/FormBinder.java
app/src/main/java/com/example/bmiassignment/validation/DecimalDigitsInputFilter.java
app/src/main/java/com/example/bmiassignment/validation/InputError.java
app/src/main/java/com/example/bmiassignment/validation/InputValidationResult.java
app/src/main/java/com/example/bmiassignment/validation/InputValidator.java
app/src/main/res/layout/view_bmi_form.xml
app/src/test/java/com/example/bmiassignment/validation/InputValidatorTest.java
app/src/androidTest/java/com/example/bmiassignment/ui/FormBinderTest.java
```

### กติกา Input

- จำนวนเต็มก่อนจุดทศนิยมไม่เกิน 8 หลัก
- ตัวเลขหลังจุดทศนิยมไม่เกิน 2 หลัก
- ใช้เลขอารบิก `0–9` และจุดทศนิยม `.`
- ค่าต้องมากกว่า `0`
- ไม่รับ comma, exponent, `NaN` หรือ `Infinity`

## 6. สมาชิก D — Responsive Layout และ State Restoration

### งานที่พัฒนา

- สร้าง Layout แนวตั้งสำหรับหน้าจอทั่วไป
- สร้าง Layout แนวนอน
- สร้าง Layout สองคอลัมน์สำหรับหน้าจอตั้งแต่ 600dp
- ใช้ `ScrollView` เพื่อรองรับพื้นที่แนวตั้งและ Font size ขนาดใหญ่
- ใช้ `include` เพื่อไม่ทำสำเนา Form และ Result View
- สร้าง `UiState` และ `UiStateStore`
- เก็บข้อความ Error ผลคำนวณล่าสุด และสถานะผิดพลาด
- คืนข้อมูลโดยไม่เก็บ Activity, View หรือข้อความที่แปลแล้ว

### ไฟล์สำคัญ

```text
app/src/main/res/layout/activity_main.xml
app/src/main/res/layout-land/activity_main.xml
app/src/main/res/layout-w600dp/activity_main.xml
app/src/main/res/values/dimens.xml
app/src/main/java/com/example/bmiassignment/state/UiState.java
app/src/main/java/com/example/bmiassignment/state/UiStateStore.java
app/src/androidTest/java/com/example/bmiassignment/state/UiStateStoreTest.java
```

### การตรวจสอบ

- แนวตั้ง
- แนวนอน
- หน้าจอกว้างตั้งแต่ 600dp
- Font size ขนาดใหญ่
- การหมุนหน้าจอ
- State ที่สมบูรณ์และ State ที่ข้อมูลบางส่วนผิดรูปแบบ

## 7. สมาชิก E — Localization และ Result Formatting

### งานที่พัฒนา

- สร้างข้อความภาษาอังกฤษ
- สร้างข้อความภาษาไทยด้วย key ชุดเดียวกัน
- แปลง `BmiCategory` เป็นข้อความตามภาษา
- แปลง `InputError` เป็นข้อความตามภาษา
- จัดรูปแบบค่า BMI ด้วยทศนิยม 2 ตำแหน่ง
- ปัดเศษแบบ `HALF_UP`
- ใช้ Locale จาก Resources ปัจจุบัน
- ไม่ใส่ตัวคั่นหลักพัน
- สร้างข้อความใหม่หลัง Activity ถูกสร้างใหม่

### ไฟล์สำคัญ

```text
app/src/main/java/com/example/bmiassignment/presentation/ResultText.java
app/src/main/java/com/example/bmiassignment/presentation/BmiTextFormatter.java
app/src/main/res/values/strings.xml
app/src/main/res/values-th/strings.xml
app/src/androidTest/java/com/example/bmiassignment/presentation/BmiTextFormatterTest.java
```

ภาษาอังกฤษและภาษาไทยมี String Resource อย่างละ 26 keys และ Android เลือกภาษาตามภาษาของอุปกรณ์โดยอัตโนมัติ

## 8. ลำดับการทำงานร่วมกัน

```text
ผู้ใช้กรอกข้อมูล
        ↓
FormBinder
        ↓
InputValidator
        ↓
BmiCalculator
        ↓
BmiTextFormatter
        ↓
ResultBinder
        ↓
แสดงค่า BMI หมวด และสี
```

`MainActivity` เป็นผู้เชื่อมแต่ละส่วน และใช้ `UiStateStore` เพื่อบันทึกกับคืนสถานะ

## 9. Public Contracts ที่ใช้ร่วมกัน

| ส่วน | API สำคัญ |
|---|---|
| BMI core | `BmiCalculator.calculate(...)`, `BmiCalculator.classify(...)` |
| Validation | `InputValidator.validate(...)` |
| Form | `FormBinder.snapshot()`, `FormBinder.restore(...)` |
| Formatting | `BmiTextFormatter.format(...)`, `inputError(...)` |
| Result | `ResultBinder.showEmpty(...)`, `showResult(...)`, `showError(...)` |
| State | `UiStateStore.save(...)`, `UiStateStore.restore(...)` |

`BmiCategory` มี 8 ค่า:

```text
SEVERE_THINNESS
MODERATE_THINNESS
MILD_THINNESS
NORMAL
OVERWEIGHT
OBESE_I
OBESE_II
OBESE_III
```

`InputError` มี 4 ค่า:

```text
NONE
REQUIRED
INVALID_NUMBER
NON_POSITIVE
```

## 10. UI และ Resources

แอปใช้แนวทาง UI ชื่อ **BMI Compass** และแยกค่าที่แสดงออกจาก Layout กับ Java:

- ข้อความอังกฤษอยู่ใน `res/values/strings.xml`
- ข้อความไทยอยู่ใน `res/values-th/strings.xml`
- สี Light mode อยู่ใน `res/values/`
- สี Dark mode อยู่ใน `res/values-night/`
- ขนาดและระยะห่างอยู่ใน `res/values/dimens.xml`
- พื้นหลัง Card อยู่ใน `res/drawable/`
- ขนาดข้อความใช้ `sp`
- ขนาดและระยะห่างใช้ `dp`

ไม่มีข้อความ UI สี หรือขนาดที่จำเป็นต่อการแสดงผล Hard-code อยู่ใน Java

## 11. การจำแนก BMI

| BMI | `BmiCategory` |
|---:|---|
| `< 16.0` | `SEVERE_THINNESS` |
| `16.0 - < 17.0` | `MODERATE_THINNESS` |
| `17.0 - < 18.5` | `MILD_THINNESS` |
| `18.5 - < 25.0` | `NORMAL` |
| `25.0 - < 30.0` | `OVERWEIGHT` |
| `30.0 - < 35.0` | `OBESE_I` |
| `35.0 - < 40.0` | `OBESE_II` |
| `≥ 40.0` | `OBESE_III` |

ระบบใช้ค่าจริงก่อนปัดเศษในการจำแนก เช่น `24.999` ยังเป็น `NORMAL` แม้ข้อความที่แสดงจะเป็น `25.00`

## 12. การตรวจสอบหลังรวมงาน

คำสั่งที่ใช้:

```bash
./gradlew test assembleDebug assembleDebugAndroidTest
./gradlew connectedDebugAndroidTest
./gradlew lintDebug
```

ผลล่าสุดบน Pixel 6a AVD, Android 15, API 35:

- Unit Tests ผ่าน
- Debug APK Build ผ่าน
- Android-test APK Compile ผ่าน
- Instrumented Tests ผ่าน `34/34`
- Android Lint ไม่มีคำเตือนจากโค้ด Layout หรือ Resources
- เหลือเพียงคำแนะนำว่ามี Gradle และ Android Gradle Plugin รุ่นใหม่กว่า

โครงงานยังใช้รุ่นที่ผ่านการตรวจสอบแล้วเพื่อหลีกเลี่ยงความเสี่ยงจากการเปลี่ยน Build Tool ก่อนส่งงาน

## 13. สถานะสุดท้าย

- งานของสมาชิก A–E รวมเข้าด้วยกันแล้ว
- ไม่มี Dependency ที่รอจากสมาชิกอื่น
- การคำนวณและการแบ่งหมวดผ่านการทดสอบ
- Validation และข้อจำกัด 8/2 หลักผ่านการทดสอบ
- ภาษาอังกฤษและภาษาไทยผ่านการทดสอบ
- Layout แนวตั้ง แนวนอน และจอกว้างพร้อมใช้งาน
- Light mode และ Dark modeพร้อมใช้งาน
- การคืนสถานะผ่านการทดสอบ
- ไม่มีไฟล์ APK, `local.properties`, keystore หรือข้อมูลลับใน Commit