# CS364 Assignment 1: BMI Calculator

แอปพลิเคชัน Android สำหรับคำนวณค่าดัชนีมวลกาย  
(Body Mass Index: BMI) จากน้ำหนักและส่วนสูงของผู้ใช้

พัฒนาด้วย **Java และ XML Views** สำหรับ CS364 Assignment 1

## Features

- รับน้ำหนักในหน่วยกิโลกรัม
- รับส่วนสูงในหน่วยเซนติเมตร
- คำนวณและแสดงค่า BMI
- แสดงหมวดหมู่น้ำหนักตามค่า BMI
- ตรวจสอบข้อมูลก่อนคำนวณ
- รองรับภาษาไทยและอังกฤษตามภาษาของอุปกรณ์
- ปรับ Layout ตามความกว้างของหน้าจอ
- รองรับขนาดตัวอักษรจากการตั้งค่าของอุปกรณ์
- รักษาข้อมูลเมื่อเกิด Configuration Change เช่น การหมุนหน้าจอ

## BMI Calculation

สูตรคำนวณ:

```text
BMI = weightKg / (heightMeters × heightMeters)
heightMeters = heightCm / 100
```

ตัวอย่าง:

```text
Weight: 65 kg
Height: 168 cm
BMI: 23.03
Category: Normal
```

## BMI Classification

เกณฑ์ต่อไปนี้ใช้สำหรับผู้ใหญ่อายุ 20 ปีขึ้นไป

| BMI | English | ภาษาไทย |
|---:|---|---|
| `< 16.0` | Severe Thinness | ผอมมาก |
| `16.0 - < 17.0` | Moderate Thinness | ผอมปานกลาง |
| `17.0 - < 18.5` | Mild Thinness | ผอมเล็กน้อย |
| `18.5 - < 25.0` | Normal | ปกติ |
| `25.0 - < 30.0` | Overweight | น้ำหนักเกิน |
| `30.0 - < 35.0` | Obese Class I | อ้วนระดับ 1 |
| `35.0 - < 40.0` | Obese Class II | อ้วนระดับ 2 |
| `≥ 40.0` | Obese Class III | อ้วนระดับ 3 |

อ้างอิง: [Calculator.net BMI Calculator](https://www.calculator.net/bmi-calculator.html)

## Team Members

| Member | Name | Student ID | Responsibility |
|---|---|---|---|
| A | `ปพนพัชร์ มีวน` | `6709650458` | Result UI and Integration |
| B | `รณกฤต วรลักษณ์ภักดี` | `6709650607` | BMI Core and Unit Tests |
| C | `ภูริณัฐ วรรธนะธัญญา` | `6709650565` | Input Form and Validation |
| D | `ภูรินทร์ แก้วพ่วงเสก` | `6709650573` | Responsive Layout and State Restoration |
| E | `ชนกานต์ คงรัชตภิญโญ` | `670960235` | Thai/English Localization and Result Formatting |

สมาชิกทุกคนพัฒนาและ Commit โค้ดในส่วนที่ตนรับผิดชอบ

## Development Branches

| Member | Branch |
|---|---|
| A | `codex/result-integration` |
| B | `codex/bmi-core` |
| C | `codex/input-form` |
| D | `codex/layout-state` |
| E | `codex/localization` |

## Project Structure

```text
app/src/main/java/com/example/bmiassignment/
├── domain/
├── model/
├── presentation/
├── state/
├── ui/
├── validation/
└── MainActivity.java

app/src/main/res/
├── layout/
├── layout-w600dp/
├── values/
└── values-th/
```

## Build and Run

1. Clone Repository:

   ```bash
   git clone https://github.com/zero-h0ur/CS364-Assignment1-BMI.git
   ```

2. เปิดโปรเจกต์ด้วย Android Studio
3. รอให้ Gradle Sync เสร็จ
4. เลือก Android Emulator หรืออุปกรณ์จริง
5. กด **Run**

ไฟล์ `local.properties` จะถูกสร้างตาม Android SDK ของแต่ละเครื่อง และต้องไม่ Commit ลง Repository

## Testing Checklist

- [ ] โปรเจกต์ Build สำเร็จ
- [ ] คำนวณ BMI ถูกต้อง
- [ ] แบ่งหมวดหมู่ตรงตามเกณฑ์
- [ ] ตรวจค่าตรงขอบเขตทุกหมวดหมู่
- [ ] ปฏิเสธช่องว่างและข้อมูลที่ไม่ใช่ตัวเลข
- [ ] ปฏิเสธค่าศูนย์และค่าติดลบ
- [ ] ใช้งานได้ในหน้าจอแคบและกว้าง
- [ ] ข้อมูลไม่หายหลังหมุนหน้าจอ
- [ ] ภาษาเปลี่ยนตามภาษาของอุปกรณ์
- [ ] รองรับการเปลี่ยนขนาดตัวอักษร

## Team Documentation

รายละเอียดหน้าที่ โครงสร้างไฟล์ ข้อตกลง API, View ID และข้อกำหนดร่วมของทีม:

- [Team Responsibilities and Implementation Specification](docs/team-responsibilities.md)

## Screenshots

เพิ่มภาพหน้าจอเมื่อ UI พร้อมใช้งาน:

- English interface
- Thai interface
- Narrow-screen layout
- Wide-screen layout
- BMI result
- Input validation

## Demo Video

เพิ่มลิงก์วิดีโอสาธิตก่อนส่งงาน
