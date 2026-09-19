# หน้าที่และข้อกำหนดร่วมของทีมพัฒนาแอป BMI

เอกสารนี้กำหนดงานของสมาชิก 5 คน ขอบเขตไฟล์ที่แต่ละคนรับผิดชอบ และรูปแบบการส่งข้อมูลระหว่างส่วน เพื่อให้ทุกคนพัฒนางานของตนเองแล้วนำมารวมเป็นแอปเดียวกันได้ ใช้รหัสสมาชิก A ถึง E แทนชื่อ โดย A เป็นผู้พัฒนาส่วนแสดงผลและผู้รวมโครงงาน

## 1 ขอบเขตงานร่วมกัน

พัฒนาแอป Android ด้วย Java และ XML Views ใช้ package หลัก com.example.bmiassignment และโมดูล app เดียว ทุกคนเริ่มจากโครงงานตั้งต้นเดียวกัน ใช้เวอร์ชัน JDK SDK Gradle และ dependencies ตามไฟล์โครงงานที่ A จัดเตรียม การปรับเวอร์ชันเป็นหน้าที่ของ A เพื่อให้ทุกเครื่องใช้ชุดเดียวกัน

แอปรับน้ำหนักเป็นกิโลกรัมและส่วนสูงเป็นเซนติเมตร คำนวณ BMI แสดงค่าและเกณฑ์น้ำหนัก รองรับข้อความไทยและอังกฤษตามภาษาเครื่อง ปรับการจัดวางตามความกว้าง และรองรับขนาดตัวอักษรจากการตั้งค่าเครื่อง

ขอบเขตการแปลผลในเอกสารนี้ใช้เกณฑ์ผู้ใหญ่อายุ 20 ปีขึ้นไป หน่วย kg และ cm ตามฟอร์มหลัก ไม่เพิ่มช่องอายุ เพศ หรือหน่วยอื่นในส่วนใดส่วนหนึ่งโดยลำพัง หากมีข้อกำหนดจากผู้สอนเพิ่มเติม ต้องปรับข้อตกลงกลางและส่วนที่เกี่ยวข้องพร้อมกัน

สมาชิกทุกคนต้องเขียนโค้ดส่วนของตน ทดสอบงานที่รับผิดชอบ และช่วยแก้ข้อผิดพลาดในส่วนนั้นเมื่อรวมโครงงาน

## 2 ภาพรวมการแบ่งงาน

| สมาชิก | ฟีเจอร์หลัก | โค้ดหลักที่รับผิดชอบ |
|---|---|---|
| A | แสดงผล BMI และเชื่อมการทำงานทั้งแอป | MainActivity และ ResultBinder |
| B | คำนวณ BMI และจำแนกเกณฑ์น้ำหนัก | BmiCalculator และ model ของการคำนวณ |
| C | ฟอร์มรับข้อมูลและตรวจข้อมูลก่อนคำนวณ | FormBinder และ InputValidator |
| D | หน้าจอแคบและกว้าง การรองรับฟอนต์ และการเก็บสถานะ | XML layout หลัก UiState และ UiStateStore |
| E | ข้อความไทยและอังกฤษ และการจัดรูปแบบผลลัพธ์ | BmiTextFormatter และ string resources |

การทำงานปกติคือ C รับข้อมูลและตรวจความถูกต้อง จากนั้น A ส่งข้อมูลให้ B คำนวณ เรียก E เพื่อสร้างข้อความตามภาษาเครื่อง แล้ว A แสดงผลในส่วนผลลัพธ์ ส่วน D จัดพื้นที่ให้ฟอร์มและผลลัพธ์ พร้อมเก็บข้อมูลที่จำเป็นเมื่อหน้าจอถูกสร้างใหม่

## 3 สมาชิก A ส่วนแสดงผลและการเชื่อมระบบ

### งานที่ต้องทำ

1. สร้างส่วนแสดงผลที่มีค่า BMI เกณฑ์น้ำหนัก และสีประกอบเกณฑ์ โดยมีข้อความเกณฑ์ให้อ่านได้เสมอ
2. ดูแลสถานะส่วนผลลัพธ์ 3 แบบ ได้แก่ ยังไม่มีผล คำนวณสำเร็จ และคำนวณไม่สำเร็จ
3. เมื่อเริ่มแอปให้แสดงข้อความแนะนำ เมื่อคำนวณสำเร็จให้แสดงผลใหม่ และเมื่อผู้ใช้แก้ข้อมูลให้ล้างผลเก่าทันที
4. เขียน MainActivity ให้รับเหตุการณ์จาก FormBinder แล้วเรียก BmiCalculator และ BmiTextFormatter ก่อนส่งข้อมูลให้ ResultBinder
5. เรียก UiStateStore ของ D ในจุดบันทึกและคืนสถานะของ Activity และสร้างข้อความแสดงผลใหม่ด้วยภาษาปัจจุบัน
6. จัดโครงงานตั้งต้น ไฟล์ build และ Manifest รวม Pull Request และตรวจว่าทุกส่วนเรียกใช้กันตามข้อกำหนด

### ไฟล์ที่รับผิดชอบ

- MainActivity.java
- ui/ResultBinder.java
- res/layout/view_bmi_result.xml
- res/values/colors_result.xml และ drawable ที่ขึ้นต้น result_ หากจำเป็น
- AndroidManifest.xml ไฟล์ Gradle และการตั้งค่าระดับโครงงาน
- README.md ส่วนภาพรวมและสรุปหน้าที่สมาชิก

### ขอบเขตกับสมาชิกอื่น

A รับค่าและรหัสเกณฑ์จาก B โดยไม่เขียนสูตรหรือตัดช่วง BMI ซ้ำ รับข้อความสำเร็จรูปจาก E และเลือกสีจาก BmiCategory ไม่เลือกสีจากคำภาษาไทยหรืออังกฤษ D เป็นเจ้าของ layout ภายนอก ส่วน A เป็นเจ้าขององค์ประกอบภายในแผงผลลัพธ์

### เกณฑ์รับงาน

ผลใหม่ตรงกับการคำนวณล่าสุด ไม่มีผลเก่าค้างหลังแก้ input แสดงข้อความและสีตรงกับเกณฑ์ ใช้ได้ใน layout ทั้งสองแบบ และเชื่อมการคืนสถานะกับ D ได้ครบ

## 4 สมาชิก B สูตรคำนวณและเกณฑ์น้ำหนัก

### งานที่ต้องทำ

1. สร้าง BmiInput สำหรับน้ำหนักและส่วนสูง BmiResult สำหรับค่าที่คำนวณได้ และ BmiCategory สำหรับรหัสเกณฑ์
2. เขียนสูตร BMI โดยแปลงเซนติเมตรเป็นเมตรก่อนยกกำลังสอง
3. เขียนเมธอด classify ที่จำแนกเกณฑ์จากค่า BMI จริงก่อนปัดเศษ
4. ตรวจค่าตัวเลขที่ส่งเข้ามาอีกชั้นหนึ่ง ปฏิเสธค่าที่ไม่เป็นจำนวนจำกัด ค่าที่ไม่มากกว่า 0 และผลคำนวณผิดปกติ
5. เขียน unit tests ของสูตรและค่าตรงขอบช่วง ต่ำกว่าขอบ และสูงกว่าขอบ

### ไฟล์ที่รับผิดชอบ

- domain/BmiInput.java
- domain/BmiResult.java
- domain/BmiCategory.java
- domain/BmiCalculator.java
- app/src/test/java/com/example/bmiassignment/domain/BmiCalculatorTest.java

### ขอบเขตกับสมาชิกอื่น

ส่วน domain เป็น Java ปกติ ไม่ใช้ Activity View Context R.string หรือสี ไม่คืนคำว่า ปกติ หรือ Normal ให้คืน BmiCategory.NORMAL เพื่อให้ E จัดภาษาและ A แสดงผลได้โดยอิสระ

### เกณฑ์รับงาน

65 kg และ 168 cm ให้ค่าประมาณ 23.030045 ซึ่งแสดงเป็น 23.03 ในหน้าจอ ค่าขอบช่วงทั้งหมดเป็นไปตามตารางกลาง และการคำนวณทดสอบได้โดยไม่ต้องเปิด emulator

## 5 สมาชิก C ฟอร์มและการตรวจข้อมูล

### งานที่ต้องทำ

1. สร้างช่องกรอกน้ำหนัก ส่วนสูง ป้ายหน่วย และปุ่มคำนวณใน view_bmi_form.xml
2. เขียน InputValidator แปลงข้อความเป็นตัวเลข และคืน error แยกตามช่อง
3. เขียน FormBinder ผูก view กับเหตุการณ์ เมื่อกดคำนวณให้ตรวจทั้งสองช่องและส่ง BmiInput เฉพาะเมื่อข้อมูลถูกต้อง
4. แสดง error ใต้หรือที่ช่องที่มีปัญหา โดยใช้ข้อความจาก BmiTextFormatter ของ E
5. เมื่อผู้ใช้แก้ข้อความ ให้ล้าง error ของช่องที่แก้ แล้วแจ้ง A ผ่าน onInputChanged เพื่อยกเลิกผลเก่า
6. สร้าง FormSnapshot เก็บข้อความดิบและรหัส error และรองรับการคืนข้อมูลโดยไม่ยิง callback ว่าเป็นการแก้ไขของผู้ใช้
7. เขียน unit tests ของ InputValidator และตรวจการกรอกจริงบนหน้าจอ

### ไฟล์ที่รับผิดชอบ

- ui/FormBinder.java
- validation/InputValidator.java
- validation/InputError.java
- validation/InputValidationResult.java
- model/FormSnapshot.java
- res/layout/view_bmi_form.xml
- app/src/test/java/com/example/bmiassignment/validation/InputValidatorTest.java

### ขอบเขตกับสมาชิกอื่น

C ตรวจข้อความและตัวเลขที่กรอก แต่ไม่คำนวณ BMI ไม่จัดเกณฑ์ และไม่แก้ส่วนผลลัพธ์โดยตรง การกดปุ่มที่ข้อมูลผิดให้เรียก onValidationFailed เพื่อให้ A ล้างผลเดิม ส่วนข้อความ error ทุกภาษาอยู่กับ E

### เกณฑ์รับงาน

ช่องว่าง ตัวเลขผิด 0 และค่าลบมีข้อความแจ้งที่ถูกช่อง ไม่เกิด crash รองรับทศนิยมตามข้อกำหนดกลาง คืนข้อความเดิมหลังสร้างหน้าจอใหม่ได้ และส่ง callback เฉพาะตามเงื่อนไขที่กำหนด

## 6 สมาชิก D หน้าจอและการรักษาสถานะ

### งานที่ต้องทำ

1. สร้าง activity_main.xml สองชุด ชุดแคบเรียงฟอร์มก่อนผลลัพธ์ในแนวตั้ง ชุดกว้างวางฟอร์มและผลลัพธ์สองคอลัมน์
2. ใช้ include เพื่อดึง view_bmi_form และ view_bmi_result ของ C และ A มาใช้ร่วมกัน ไม่คัดลอกเนื้อหาภายในไปทำอีกชุด
3. จัดขนาด ระยะห่าง พื้นที่เลื่อน และพื้นที่ปลอดภัยจากแถบระบบ เพื่อให้ฟอร์มและผลอ่านได้เมื่ออักษรใหญ่
4. เขียน UiState เป็นข้อมูลสถานะหน้าจอ และ UiStateStore สำหรับบันทึกลง Bundle และอ่านกลับ
5. เก็บข้อความในฟอร์ม รหัส error ข้อมูลที่ใช้คำนวณล่าสุด และสถานะคำนวณไม่สำเร็จ โดยไม่เก็บ View Activity Context หรือข้อความแปลแล้ว
6. ทดสอบการหมุนจอ เปลี่ยนความกว้าง เปลี่ยน font size และการสร้าง Activity ใหม่ พร้อมประสาน A ในส่วนการเรียก save และ restore

### ไฟล์ที่รับผิดชอบ

- res/layout/activity_main.xml
- res/layout-w600dp/activity_main.xml
- res/values/dimens.xml และ res/values/themes.xml
- res/values/colors.xml สำหรับสีส่วนกลางที่ไม่ใช่สีผลลัพธ์
- state/UiState.java และ state/UiStateStore.java
- app/src/androidTest/java/com/example/bmiassignment/state/UiStateStoreTest.java

### ขอบเขตกับสมาชิกอื่น

D ดูแลโครงหน้าจอและวิธีเก็บข้อมูล แต่ไม่เขียน logic แสดงผลของ A หรือการอ่าน input ของ C หากพบปัญหาภายในแผง ให้ส่งตำแหน่งและเงื่อนไขที่พบให้เจ้าของแผงแก้ A เป็นผู้เรียก UiStateStore จาก MainActivity จึงไม่ต้องแก้ MainActivity แข่งกัน

### เกณฑ์รับงาน

หน้าต่างต่ำกว่า 600dp ใช้แบบแคบ ตั้งแต่ 600dp ใช้แบบกว้าง อักษรใหญ่แล้วยังอ่านและกดใช้งานได้ และข้อมูลที่บันทึกไว้คืนกลับได้โดยผลลัพธ์ไม่ค้างภาษาเดิม

## 7 สมาชิก E ภาษาและรูปแบบข้อความ

### งานที่ต้องทำ

1. สร้างข้อความอังกฤษใน values/strings.xml และไทยใน values-th/strings.xml ให้ครบชุดเดียวกัน
2. เขียน BmiTextFormatter แปลง BmiCategory และ InputError เป็นข้อความที่ตรงกับภาษาใน Context ปัจจุบัน
3. จัดค่า BMI เป็นข้อความทศนิยม 2 ตำแหน่ง โดยใช้ locale ของ resources ปัจจุบันและไม่ใส่ตัวคั่นหลักพัน
4. สร้าง ResultText สำหรับส่งข้อความค่า BMI และข้อความเกณฑ์ให้ A
5. เตรียมข้อความสถานะยังไม่มีผล คำนวณไม่สำเร็จ ป้าย หน่วย ปุ่ม และคำอธิบายที่ใช้ในหน้าจอให้ครบ
6. ทดสอบการเปลี่ยนภาษา ทั้งข้อความคงที่ ผลที่คำนวณแล้ว และ error ที่กำลังแสดงอยู่

### ไฟล์ที่รับผิดชอบ

- presentation/BmiTextFormatter.java
- presentation/ResultText.java
- res/values/strings.xml
- res/values-th/strings.xml
- app/src/androidTest/java/com/example/bmiassignment/presentation/BmiTextFormatterTest.java

### ขอบเขตกับสมาชิกอื่น

E จัดภาษาและรูปแบบข้อความ แต่ไม่คำนวณหรือตัดช่วง BMI ซ้ำ และไม่กำหนดการจัดวางหน้าจอ A กับ C เรียก formatter เพื่อขอข้อความ สมาชิกอื่นส่งคำขอเพิ่ม string key ให้ E ดูแลไฟล์กลาง

### เกณฑ์รับงาน

เปลี่ยนไทยและอังกฤษแล้วข้อความครบทุกส่วน ไม่มีข้อความ hardcode ค้างภาษาเดิม ค่า BMI มีทศนิยม 2 ตำแหน่ง และมี default strings ครบสำหรับกรณีภาษาที่ไม่ได้รองรับ

## 8 โครงสร้างไฟล์ร่วม

ตำแหน่ง Java ทั้งหมดด้านล่างอยู่ใต้ app/src/main/java/com/example/bmiassignment ส่วน resources อยู่ใต้ app/src/main/res

```text
MainActivity.java                         A
model/FormSnapshot.java                   C
domain/BmiInput.java                       B
domain/BmiResult.java                      B
domain/BmiCategory.java                    B
domain/BmiCalculator.java                  B
validation/InputError.java                 C
validation/InputValidationResult.java      C
validation/InputValidator.java             C
ui/FormBinder.java                        C
ui/ResultBinder.java                      A
presentation/ResultText.java               E
presentation/BmiTextFormatter.java         E
state/UiState.java                         D
state/UiStateStore.java                    D
```

ทุก public class หรือ enum แยกไฟล์ตามชื่อ ใช้ชื่อคลาสแบบ PascalCase เมธอดและตัวแปรแบบ camelCase และชื่อ resource แบบ snake_case ไม่เปลี่ยน public signature หรือชื่อ resource ที่คนอื่นใช้โดยไม่อัปเดตข้อตกลงและแจ้งผู้เรียกใช้งาน

## 9 ชนิดข้อมูลและความหมาย

ทุก model ใช้ public final class มี public final fields และ constructor รับค่าตามลำดับที่ระบุ ไม่มี setter ส่วน String และ enum ต้องไม่เป็น null เว้นแต่ระบุไว้เฉพาะช่องนั้น

| คลาสและเจ้าของ | Fields ตามลำดับ constructor | ความหมาย |
|---|---|---|
| BmiInput ของ B | double weightKg, double heightCm | ตัวเลขที่จะส่งเข้าคำนวณ หน่วยตายตัว |
| BmiResult ของ B | double bmi, BmiCategory category | ผลดิบที่ยังไม่ format เป็นข้อความ |
| InputValidationResult ของ C | BmiInput input, InputError weightError, InputError heightError | input เป็น null เมื่อมี error; เพิ่ม boolean isValid() |
| FormSnapshot ของ C | String weightText, String heightText, InputError weightError, InputError heightError | ข้อความที่อยู่ในฟอร์มและรหัส error ล่าสุด |
| ResultText ของ E | String valueText, String categoryText | ข้อความพร้อมแสดง ไม่มีหน้าที่ตัดเกณฑ์หรือเลือกสี |
| UiState ของ D | FormSnapshot form, BmiInput lastCalculatedInput, boolean calculationFailed | ข้อมูลหน้าจอ; lastCalculatedInput เป็น null เมื่อไม่มีผลสำเร็จ |

InputValidationResult ที่สำเร็จต้องมี input และ error ทั้งคู่เป็น NONE กรณีไม่สำเร็จต้องมี input เป็น null และมี error อย่างน้อยหนึ่งช่อง

UiState ต้องไม่อยู่ในสถานะมี lastCalculatedInput พร้อมกับ calculationFailed เป็น true ค่าเริ่มต้นคือฟอร์มข้อความว่าง error เป็น NONE ทั้งคู่ lastCalculatedInput เป็น null และ calculationFailed เป็น false

ใช้ enum InputError ของ C ดังนี้

```java
public enum InputError {
    NONE, REQUIRED, INVALID_NUMBER, NON_POSITIVE
}
```

ใช้ enum BmiCategory ของ B ดังนี้

```java
public enum BmiCategory {
    SEVERE_THINNESS, MODERATE_THINNESS, MILD_THINNESS,
    NORMAL, OVERWEIGHT, OBESE_I, OBESE_II, OBESE_III
}
```

## 10 เมธอดที่แต่ละส่วนต้องเปิดให้เรียก

โค้ดด้านล่างระบุชื่อ ชนิดข้อมูล และรูปแบบการเรียก เป็นสัญญาของทีม เจ้าของแต่ละคลาสต้องเขียน implementation และ constructor ของ model ให้ครบเอง

### ส่วนคำนวณของ B

```java
public final class BmiCalculator {
    public static BmiResult calculate(BmiInput input);
    public static BmiCategory classify(double bmi);
}
```

calculate และ classify ต้องปฏิเสธค่าที่ไม่เป็นจำนวนจำกัดหรือไม่มากกว่า 0 ด้วย IllegalArgumentException ถ้า input เป็น null ให้ใช้ exception ชนิดเดียวกัน calculate ต้องตรวจ intermediate และผลลัพธ์เพื่อไม่คืน NaN Infinity หรือค่า 0 จาก underflow ข้อความ exception ไม่ใช่ข้อความ UI

### ส่วนฟอร์มของ C

```java
public final class InputValidator {
    public static InputValidationResult validate(
        String weightText, String heightText);
}

public final class FormBinder {
    public interface Listener {
        void onCalculateRequested(BmiInput input);
        void onInputChanged();
        void onValidationFailed();
    }
    public FormBinder(View root,
        BmiTextFormatter formatter, Listener listener);
    public FormSnapshot snapshot();
    public void restore(FormSnapshot snapshot);
}
```

เมื่อกดคำนวณ C ต้องอ่านข้อมูลทั้งสองช่อง ตรวจและอัปเดต error ก่อนเรียก callback เพียงชนิดเดียว ถ้าผ่านเรียก onCalculateRequested ถ้าไม่ผ่านเรียก onValidationFailed การ restore ต้องไม่เรียก callback ทั้งสาม และต้องนำรหัส error มาแสดงใหม่ผ่าน formatter ปัจจุบัน

### ส่วนแสดงผลของ A

```java
public final class ResultBinder {
    public ResultBinder(View root);
    public void showEmpty(String message);
    public void showResult(BmiResult result, ResultText text);
    public void showError(String message);
}
```

ทุกเมธอดต้องล้างข้อความ สี และ visibility ที่ไม่เกี่ยวข้องจากสถานะก่อนหน้า showResult ใช้ result.category เลือกสี และใช้ text สำหรับข้อความ ห้ามจำแนกเกณฑ์จากค่าที่ปัดเศษหรือจากคำแปล

### ส่วนภาษาและข้อความของ E

```java
public final class BmiTextFormatter {
    public BmiTextFormatter(Context context);
    public ResultText format(BmiResult result);
    public String inputError(InputError error);
    public String emptyMessage();
    public String calculationErrorMessage();
}
```

inputError(NONE) คืนสตริงว่าง C แปลงกรณีนี้เป็นการล้าง error ของ View ใช้ formatter ที่สร้างจาก Context ของ Activity ปัจจุบัน ไม่เก็บไว้ใน static field และไม่บันทึกลง UiState

### ส่วนเก็บสถานะของ D

```java
public final class UiStateStore {
    public static void save(Bundle outState, UiState state);
    public static UiState restore(Bundle savedState);
}
```

restore(null) คืนสถานะเริ่มต้น บันทึกเป็น primitive และ String ภายใต้ key ที่ขึ้นต้น bmi_state. เก็บ enum ด้วย name() ไม่ใช้ ordinal() และอ่านกลับโดยมีค่าเริ่มต้นรองรับ key ที่หายหรือ enum ที่ไม่รู้จัก ถ้าข้อมูลส่วนผลไม่ครบหรือผิดรูปแบบ ให้ทิ้งเฉพาะผลนั้นและคืนฟอร์มที่ยังใช้ได้

## 11 กติกาการรับข้อมูลและคำนวณ

ตัดช่องว่างหน้าและท้ายก่อนตรวจ ช่องว่างล้วนใช้ REQUIRED รูปแบบไม่ใช่ตัวเลขใช้ INVALID_NUMBER และเลข 0 หรือติดลบใช้ NON_POSITIVE ทั้งสองช่องต้องเป็น double ที่มีค่าจำกัด

รับเลขอารบิก 0 ถึง 9 และจุดทศนิยม เช่น 65, 65.5 และ .5 อนุญาตเครื่องหมายบวกหรือลบนำหน้าเพื่อให้ตรวจ NON_POSITIVE ได้ชัดเจน ไม่รับ comma คั่นหลักพัน ตัวอักษร exponent NaN หรือ Infinity C ต้องตรวจรูปแบบก่อนใช้ Double.parseDouble และตรวจค่าหลัง parse อีกครั้ง

สูตรที่ B ต้องใช้คือ BMI = weightKg / ((heightCm / 100.0) * (heightCm / 100.0)) เก็บค่าดิบไว้ใน BmiResult และจัดเกณฑ์ก่อนปัดเศษ E เป็นผู้ปัดเพื่อแสดงผลเป็น 2 ตำแหน่งด้วย HALF_UP

| BmiCategory | เงื่อนไข | string key | สีผลลัพธ์ |
|---|---|---|---|
| SEVERE_THINNESS | bmi < 16 | category_severe_thinness | bmi_underweight |
| MODERATE_THINNESS | 16 <= bmi < 17 | category_moderate_thinness | bmi_underweight |
| MILD_THINNESS | 17 <= bmi < 18.5 | category_mild_thinness | bmi_underweight |
| NORMAL | 18.5 <= bmi < 25 | category_normal | bmi_normal |
| OVERWEIGHT | 25 <= bmi < 30 | category_overweight | bmi_overweight |
| OBESE_I | 30 <= bmi < 35 | category_obese_i | bmi_obese |
| OBESE_II | 35 <= bmi < 40 | category_obese_ii | bmi_obese |
| OBESE_III | bmi >= 40 | category_obese_iii | bmi_obese |

ค่าที่ไม่ผ่านเงื่อนไขพื้นฐานต้องถูกปฏิเสธก่อนเข้าตาราง ตัวอย่าง 24.999 จัดเป็น NORMAL แม้แสดงเป็น 25.00 เนื่องจากหน้าจอแสดงเพียง 2 ตำแหน่ง ห้ามนำค่าที่ปัดแล้วไปตัดสินเกณฑ์ใหม่

A กำหนดสีข้อความเกณฑ์ใน colors_result.xml เป็น bmi_underweight #1565C0, bmi_normal #2E7D32, bmi_overweight #8D4E00 และ bmi_obese #B71C1C ใช้พื้นหลังส่วนผลลัพธ์สีขาวและมีข้อความเกณฑ์กำกับเสมอ สีเป็นส่วนช่วยอ่าน ไม่ใช่ข้อมูลเพียงอย่างเดียว

เกณฑ์ผู้ใหญ่และสูตรอ้างอิง https://www.calculator.net/bmi-calculator.html ตารางนี้เป็นข้อตกลง implementation ของทีม โดยกำหนดขอบช่วงไม่ให้ซ้อนกัน

## 12 ข้อกำหนดของหน้าจอและ resources

ใช้ activity_main.xml สองชุด โดย layout ปกติรองรับความกว้างต่ำกว่า 600dp และ layout-w600dp รองรับความกว้างตั้งแต่ 600dp ทั้งสองชุด include ฟอร์มและผลครั้งละหนึ่งชุด และคง ID กับชนิด View เดียวกัน

| View ID | ชนิด View | เจ้าของ | หน้าที่ |
|---|---|---|---|
| screen_root | LinearLayout | D | พื้นที่จัดวางภายใน ScrollView |
| panel_form | LinearLayout | C | root ของ view_bmi_form |
| input_weight | EditText | C | ข้อความน้ำหนัก |
| input_height | EditText | C | ข้อความส่วนสูง |
| button_calculate | Button | C | ส่งคำขอคำนวณ |
| panel_result | LinearLayout | A | root ของ view_bmi_result |
| text_result_message | TextView | A | ข้อความสถานะว่างหรือผิดพลาด |
| text_bmi_value | TextView | A | ค่า BMI พร้อมหน่วย |
| text_bmi_category | TextView | A | คำแปลเกณฑ์น้ำหนัก |

root ของไฟล์ที่ include ต้องมี ID ของตัวเอง และไม่กำหนด ID ใหม่ทับบน include ให้ Binder รับ root ของ panel ที่ตนเป็นเจ้าของ ทุก View ที่เก็บข้อความหรือคืนสถานะต้องมี ID คงที่ สำหรับ input_weight และ input_height ให้ C ตั้ง android:saveEnabled="false" และคืนข้อความผ่าน FormSnapshot เท่านั้น เพื่อไม่ให้การคืนค่าของระบบซ้อนกับกลไกกลาง

D ดูแลธีม Light สำหรับโครงงานนี้ ใช้ข้อความเป็น sp ระยะห่างและขนาดองค์ประกอบเป็น dp ใช้ wrap_content ในส่วนที่ต้องขยายตามข้อความ และใช้ ScrollView ที่มี child เดียวครอบพื้นที่หลัก ไม่ล็อกแนวหน้าจอหรือปรับ fontScale เป็น 1

D กำหนด dimens กลาง ได้แก่ screen_padding 16dp, section_gap 16dp, field_gap 12dp, text_body 16sp, text_title 24sp และ text_bmi_value_size 32sp A กับ C อ้างอิงค่าเหล่านี้แทนการตั้งค่าซ้ำ หากต้องเพิ่มขนาดให้เพิ่ม key ใหม่โดยประสาน D

A ใช้กลไกสร้าง Activity ใหม่ตามปกติ ไม่ประกาศ configChanges เพื่อข้าม locale fontScale หรือ screenSize แล้วใช้ UiStateStore คืนข้อมูล

## 13 ข้อความกลางที่ E ต้องจัดเตรียม

ชื่อ key ต้องตรงกันใน values/strings.xml และ values-th/strings.xml ทุกข้อความที่ผู้ใช้เห็นต้องมาจาก resources รวมข้อความ error และข้อความที่เปลี่ยนตามผลลัพธ์

| Key | English | ไทย |
|---|---|---|
| app_name | BMI Calculator | เครื่องคำนวณ BMI |
| app_title | Body Mass Index | ดัชนีมวลกาย |
| adult_scope | Adult classification for ages 20 and over | เกณฑ์สำหรับผู้ใหญ่อายุ 20 ปีขึ้นไป |
| label_weight | Weight (kg) | น้ำหนัก (กิโลกรัม) |
| label_height | Height (cm) | ส่วนสูง (เซนติเมตร) |
| action_calculate | Calculate | คำนวณ |
| result_title | Result | ผลการคำนวณ |
| label_bmi | BMI | ค่าดัชนีมวลกาย |
| label_category | Weight category | เกณฑ์น้ำหนัก |
| result_empty | Enter weight and height, then tap Calculate. | กรอกน้ำหนักและส่วนสูง แล้วกดคำนวณ |
| error_required | Please enter a value. | กรุณากรอกข้อมูล |
| error_invalid_number | Enter a number using a decimal point. | กรุณากรอกตัวเลข โดยใช้จุดสำหรับทศนิยม |
| error_non_positive | Enter a value greater than zero. | กรุณากรอกค่าที่มากกว่า 0 |
| error_calculation | Unable to calculate. Check the values entered. | ไม่สามารถคำนวณได้ กรุณาตรวจสอบค่าที่กรอก |
| bmi_value_format | %1$s kg/m² | %1$s กก./ม.² |
| category_severe_thinness | Severe thinness | ผอมมาก |
| category_moderate_thinness | Moderate thinness | ผอมปานกลาง |
| category_mild_thinness | Mild thinness | ผอมเล็กน้อย |
| category_normal | Normal | ปกติ |
| category_overweight | Overweight | น้ำหนักเกิน |
| category_obese_i | Obese class I | อ้วนระดับ 1 |
| category_obese_ii | Obese class II | อ้วนระดับ 2 |
| category_obese_iii | Obese class III | อ้วนระดับ 3 |

E format ตัวเลขก่อน แล้วใส่ใน bmi_value_format เพื่อสร้าง ResultText.valueText ส่วน categoryText มาจาก key ของ enum นั้นโดยตรง ไม่ใส่เงื่อนไข BMI ซ้ำใน formatter

## 14 พฤติกรรมร่วมเมื่อเกิดเหตุการณ์

### เปิดหน้าจอครั้งแรก

A สร้าง formatter และ binders จาก View ของ layout ปัจจุบัน เรียก UiStateStore.restore(null) คืนฟอร์มว่างและเรียก ResultBinder.showEmpty ด้วยข้อความจาก E

### กดคำนวณด้วยข้อมูลถูกต้อง

C เรียก onCalculateRequested A เรียก BmiCalculator.calculate ถ้าสำเร็จให้เก็บ input คู่นี้เป็น lastCalculatedInput ตั้ง calculationFailed เป็น false แล้วเรียก formatter และ showResult ตามลำดับ

### ข้อมูลไม่ถูกต้องหรือคำนวณไม่ได้

ถ้า validation ไม่ผ่าน C แสดง error ของช่องและเรียก onValidationFailed A ล้าง lastCalculatedInput ตั้ง calculationFailed เป็น false และแสดงสถานะว่าง ถ้า B ปฏิเสธการคำนวณด้วย IllegalArgumentException ให้ A ล้าง lastCalculatedInput ตั้ง calculationFailed เป็น true และแสดง showError ด้วยข้อความจาก E ไม่แสดงข้อความ exception ให้ผู้ใช้

### ผู้ใช้แก้ข้อมูลหลังคำนวณ

C ล้าง error ของช่องที่แก้และเรียก onInputChanged A ล้าง lastCalculatedInput ตั้ง calculationFailed เป็น false และกลับไปแสดงสถานะว่าง ต้องคำนวณใหม่ก่อนแสดงผลอีกครั้ง

### หมุนจอหรือเปลี่ยนภาษาและขนาดอักษร

ก่อน Activity ถูกทำลาย A สร้าง UiState จาก formBinder.snapshot และสถานะผลปัจจุบัน แล้วเรียก UiStateStore.save ใน onSaveInstanceState พร้อมเรียก super ตาม lifecycle ปกติ

เมื่อสร้างใหม่ A โหลด layout และสร้าง formatter/binders ใหม่ คืนฟอร์มด้วย restore โดยไม่ยิง callback หากมี lastCalculatedInput ให้คำนวณใหม่ด้วย B และ format ด้วย E ถ้า calculationFailed เป็น true ให้สร้างข้อความ error ใหม่ มิฉะนั้นแสดงสถานะว่าง

A คืน FormSnapshot ใน onCreate หลังสร้าง binders โดย C ระงับ callback ระหว่าง restore และปิดการบันทึก View state อัตโนมัติของช่องกรอกตามข้อกำหนดข้อ 12 เพื่อให้มีแหล่งคืนค่าชุดเดียว

## 15 การตรวจรับและการส่งต่องาน

| เจ้าของ | สิ่งที่ต้องตรวจและแนบผล |
|---|---|
| A | สถานะว่าง สำเร็จ ผิดพลาด สีเกณฑ์ การคำนวณซ้ำ และการล้างผลเมื่อแก้ input |
| B | สูตรปกติ และค่า 16, 17, 18.5, 25, 30, 35, 40 รวมค่าก่อนและหลังขอบ |
| C | ช่องว่าง ทศนิยม 0 ค่าลบ ข้อความผิด และ restore ที่ไม่ยิง callback |
| D | ความกว้างต่ำกว่าและตั้งแต่ 600dp ฟอนต์ใหญ่ การหมุนจอ และ state save/restore |
| E | ไทยและอังกฤษ ข้อความ error เกณฑ์ทุก enum และ format 2 ตำแหน่ง |

ใช้ข้อมูลร่วม 65 kg / 168 cm ได้ 23.03 และ NORMAL, 70 kg / 175 cm ได้ 22.86 และ NORMAL, 100 kg / 200 cm ได้ 25.00 และ OVERWEIGHT สำหรับค่าขอบช่วง ใช้ส่วนสูง 200 cm และน้ำหนักเท่ากับ BMI ที่ต้องการคูณ 4

A ตรวจเส้นทางรวมโดยมีเจ้าของแต่ละส่วนช่วยตรวจผล ทดสอบไทยและอังกฤษร่วมกับหน้าจอแคบและกว้าง และฟอนต์ปกติกับใหญ่ แต่ละคนรับผิดชอบแก้ defect ในไฟล์ของตน

ใช้ branch แยกตามงาน ได้แก่ codex/result-integration, codex/bmi-core, codex/input-form, codex/layout-state และ codex/localization ทุกคน commit ด้วยบัญชีของตนและเปิด Pull Request ให้ A รวม ไม่เปลี่ยนไฟล์ของคนอื่นโดยไม่ประสานเจ้าของไฟล์

คำอธิบาย Pull Request ใช้แบบฟอร์มเดียวกันดังนี้

```text
เจ้าของงาน:
ฟีเจอร์หรือพฤติกรรมที่เพิ่ม:
ไฟล์ที่แก้:
public API หรือ resource key ที่เปลี่ยน:
วิธีทดสอบและผลที่ได้:
ภาพหน้าจอเมื่อมีการเปลี่ยน UI:
ส่วนที่ต้องให้สมาชิกอื่นช่วยเชื่อม:
```

A จัด baseline ที่มีไฟล์เปล่าและ signatures กลางตามเอกสารให้ build ผ่านก่อนแจก สมาชิกแต่ละคนเติม implementation ในไฟล์ที่เป็นเจ้าของ หากส่วนอื่นยังไม่พร้อมให้ใช้ model ตัวอย่างใน test ของตัวเอง ห้ามใส่ค่าผลลัพธ์จำลองค้างไว้ในเส้นทางใช้งานจริง
