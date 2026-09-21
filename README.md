# CS364 Assignment 1: BMI Calculator

แอปพลิเคชัน Android สำหรับคำนวณดัชนีมวลกาย
พัฒนาด้วย **Java และ XML Views** สำหรับงาน CS364 Assignment 1

แอปรับน้ำหนักเป็นกิโลกรัมและส่วนสูงเป็นเซนติเมตร คำนวณ BMI แสดงผลเป็นทศนิยม 2 ตำแหน่ง และแสดงหมวด BMI พร้อมสีประกอบ

## Features

- รับน้ำหนักในหน่วยกิโลกรัม
- รับส่วนสูงในหน่วยเซนติเมตร
- รองรับจำนวนเต็มไม่เกิน 8 หลัก
- รองรับทศนิยมไม่เกิน 2 ตำแหน่ง
- ตรวจข้อมูลว่าง รูปแบบตัวเลข ศูนย์ และค่าติดลบ
- คำนวณ BMI และแสดงทศนิยม 2 ตำแหน่ง
- แสดงหมวด BMI พร้อมสีประกอบ
- คำนวณซ้ำและแสดงผลจากข้อมูลล่าสุด
- รองรับภาษาอังกฤษและภาษาไทยตามภาษาของอุปกรณ์
- รองรับ Light mode และ Dark mode
- มี Layout แยกสำหรับแนวตั้งและแนวนอน
- ใช้ Layout สองคอลัมน์สำหรับจอกว้างตั้งแต่ 600dp
- รองรับ Font size จากการตั้งค่าของอุปกรณ์
- เก็บช่องกรอก Error และผลลัพธ์เมื่อ Activity ถูกสร้างใหม่

## Input Requirements

ช่องน้ำหนักและส่วนสูงใช้ข้อกำหนดเดียวกัน:

- จำนวนเต็มก่อนจุดทศนิยมไม่เกิน 8 หลัก
- ตัวเลขหลังจุดทศนิยมไม่เกิน 2 หลัก
- ใช้จุด `.` เป็นเครื่องหมายทศนิยม
- ค่าต้องมากกว่า `0`
- ไม่รับ `NaN`, `Infinity`, comma หรือ exponential notation
- ตรวจทั้งระหว่างกรอกและเมื่อกดคำนวณ

ตัวอย่างค่าที่รับ:

```text
65
65.5
65.50
12345678.90
```

ตัวอย่างค่าที่ไม่รับ:

```text
123456789
65.123
65,5
1.68e2
0
-65
```

## BMI Calculation

สูตรที่ใช้:

```text
heightMeters = heightCm / 100
BMI = weightKg / (heightMeters × heightMeters)
```

ตัวอย่าง:

```text
Weight: 65 kg
Height: 168 cm
BMI: 23.03
Category: Normal
```

แอปใช้ค่าจริงที่ยังไม่ปัดเศษสำหรับจำแนกหมวด BMI จากนั้นจึงจัดรูปแบบค่าที่แสดงด้วยทศนิยม 2 ตำแหน่งและการปัดแบบ `HALF_UP`

## BMI Classification

เกณฑ์นี้ใช้สำหรับผู้ใหญ่อายุ 20 ปีขึ้นไป

| BMI | English | ภาษาไทย |
|---:|---|---|
| `< 16.0` | Severe thinness | ผอมมาก |
| `16.0 - < 17.0` | Moderate thinness | ผอมปานกลาง |
| `17.0 - < 18.5` | Mild thinness | ผอมเล็กน้อย |
| `18.5 - < 25.0` | Normal | ปกติ |
| `25.0 - < 30.0` | Overweight | น้ำหนักเกิน |
| `30.0 - < 35.0` | Obese class I | อ้วนระดับ 1 |
| `35.0 - < 40.0` | Obese class II | อ้วนระดับ 2 |
| `≥ 40.0` | Obese class III | อ้วนระดับ 3 |

## UI and Resource Design

หน้าจอใช้แนวทาง **BMI Compass** ซึ่งใช้สีเขียวอมฟ้าสื่อถึงสุขภาพและความสมดุล พร้อมสีแยกตามหมวด BMI

องค์ประกอบ UI ไม่เขียนค่าที่แสดงต่อผู้ใช้โดยตรงใน Layout หรือ Java:

- ข้อความอังกฤษอยู่ใน `res/values/strings.xml`
- ข้อความไทยอยู่ใน `res/values-th/strings.xml`
- สี Light mode อยู่ใน `res/values/`
- สี Dark mode อยู่ใน `res/values-night/`
- ขนาดและระยะห่างอยู่ใน `res/values/dimens.xml`
- แนวตั้งอยู่ใน `res/layout/activity_main.xml`
- แนวนอนอยู่ใน `res/layout-land/activity_main.xml`
- จอกว้างอยู่ใน `res/layout-w600dp/activity_main.xml`

ขนาดข้อความใช้หน่วย `sp` เพื่อปรับตาม Font size ของอุปกรณ์

## Team Members

| Member | Name | Student ID | Responsibility |
|---|---|---|---|
| A | ปพนพัชร์ มีวน | 6709650458 | Result UI, project baseline and final integration |
| B | รณกฤต วรลักษณ์ภักดี | 6709650607 | BMI core calculation and boundary tests |
| C | ภูริณัฐ วรรธนะธัญญา | 6709650565 | Input form and validation |
| D | ภูรินทร์ แก้วพ่วงเสก | 6709650573 | Responsive layout and state restoration |
| E | ชนกานต์ คงรัชตภิญโญ | 6709650235 | Thai/English localization and result formatting |

Pull Requests ที่ใช้รวมงานหลัก:

| Work | Pull Request |
|---|---:|
| Project baseline | #11 |
| Result UI and integration | #12 |
| BMI core | #14 |
| Input form and validation | #13 |
| Responsive layout and state restoration | #10 |
| Localization and result formatting | #9 |

## Development Environment

| Component | Version |
|---|---|
| Android Studio | Quail 3 — 2026.1.3 |
| Language | Java |
| Java source compatibility | Java 11 |
| Gradle JVM toolchain | JDK 25 |
| Gradle Wrapper | 9.5.0 |
| Android Gradle Plugin | 9.3.3 |
| Minimum SDK | API 28 — Android 9 |
| Compile SDK | API 37 |
| Target SDK | API 37 |
| Build configuration | Groovy DSL |

## Project Structure

```text
app/src/main/
├── java/com/example/bmiassignment/
│   ├── domain/
│   ├── model/
│   ├── presentation/
│   ├── state/
│   ├── ui/
│   ├── validation/
│   └── MainActivity.java
└── res/
    ├── drawable/
    ├── layout/
    ├── layout-land/
    ├── layout-w600dp/
    ├── values/
    ├── values-night/
    └── values-th/

app/src/test/
└── Unit tests

app/src/androidTest/
└── Instrumented tests
```

## Clone, Build and Run

Clone Repository:

```bash
git clone https://github.com/zero-h0ur/CS364-Assignment1-BMI.git
cd CS364-Assignment1-BMI
```

เปิดโปรเจกต์ด้วย Android Studio และรอ Gradle Sync จากนั้นเลือก Emulator หรืออุปกรณ์ Android แล้วกด **Run**

Build และรัน Unit Tests:

```bash
./gradlew test assembleDebug
```

Compile Android Tests:

```bash
./gradlew assembleDebugAndroidTest
```

รัน Instrumented Tests โดยต้องเปิด Emulator ก่อน:

```bash
./gradlew connectedDebugAndroidTest
```

Debug APK อยู่ที่:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Test Results

ทดสอบล่าสุดด้วย Pixel 6a AVD, Android 15, API 35:

- Unit tests ผ่าน
- Debug APK Build ผ่าน
- Android-test APK Compile ผ่าน
- Instrumented Tests ผ่าน `34/34`
- ทดสอบการคำนวณครั้งแรกและการคำนวณซ้ำ
- ทดสอบ Validation และข้อจำกัด 8/2 หลัก
- ทดสอบค่าตรงขอบเขต BMI ทุกหมวด
- ทดสอบภาษาอังกฤษและภาษาไทย
- ทดสอบการคืนค่าหลัง Activity ถูกสร้างใหม่
- ทดสอบแนวตั้ง แนวนอน และ Font size ขนาดใหญ่

## Assignment Checklist

- [x] ใช้ Java และ XML Views
- [x] ใช้ LinearLayout เป็นโครงสร้างหลัก
- [x] มี `layout/activity_main.xml`
- [x] มี `layout-land/activity_main.xml`
- [x] รองรับหน้าจอกว้างด้วย `layout-w600dp`
- [x] ข้อความ Hint สี และขนาดมาจาก Resource
- [x] ไม่มีข้อความ UI Hard-code ใน Java หรือ Layout
- [x] รับจำนวนเต็มไม่เกิน 8 หลัก
- [x] รับทศนิยมไม่เกิน 2 ตำแหน่ง
- [x] แสดง BMI แบบแก้ไขไม่ได้
- [x] แสดง BMI เป็นทศนิยม 2 ตำแหน่ง
- [x] แสดงหมวด BMI และสีประกอบ
- [x] คำนวณซ้ำแล้วใช้ข้อมูลล่าสุด
- [x] เปลี่ยนภาษาไทย–อังกฤษตามอุปกรณ์
- [x] รองรับ Font size ของอุปกรณ์
- [x] ข้อมูลและผลลัพธ์ไม่หายหลังหมุนหน้าจอ
- [x] Light mode และ Dark mode อ่านได้ชัดเจน
- [x] ไม่มี `local.properties`, APK, keystore หรือข้อมูลลับใน Commit


## Team Documentation

- [Team responsibilities and implementation specification](docs/team-responsibilities.md)
- [Localization and result-formatting documentation](docs/localization-handoff.md)

## Repository Safety

Repository นี้ไม่ Commit ไฟล์ต่อไปนี้:

- `local.properties`
- `.idea/`
- `.gradle/`
- `build/`
- APK และ AAB
- Keystore
- Log และไฟล์ระบบปฏิบัติการ

## Demo Video
- [Demo Video (Sources)](video/DemoVideo.mp4)
- [Youtube URL](https://youtu.be/UYuRbiQOUnw)
