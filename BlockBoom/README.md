# Block Boom (Java Swing)

## วิธีคอมไพล์และรัน
รันจากโฟลเดอร์รากของโปรเจกต์ (โฟลเดอร์ที่มี `src/`)
```
javac -encoding UTF-8 -d out src/Main.java src/model/*.java src/view/*.java src/view/components/*.java src/data/*.java
java -cp out Main
```
หรือสั่ง `./run.sh` ต้องมี JDK (Java 8 ขึ้นไป) โปรแกรมจะสร้าง/อ่านไฟล์ในโฟลเดอร์ที่สั่งรัน
- `blockblast_leaderboard.csv` เก็บคะแนนสูงสุดของแต่ละชื่อ (รูปแบบ `name,score` ใช้ไฟล์เดิมต่อได้)
- `blockblast_users.csv` เก็บบัญชีผู้ใช้ (รหัสผ่านถูกแฮช SHA-256)

## โครงสร้างโฟลเดอร์
```
src/
├── Main.java                  - จุดเริ่มโปรแกรม
├── model/                     - กติกาและข้อมูลของเกม (ไม่มี UI)
│   ├── Board.java             - กระดาน 8x8 และกติกาการวาง/เคลียร์แถว
│   ├── Piece.java             - ชิ้นบล็อก (ADT)
│   ├── PieceGenerator.java    - สุ่มสร้างชิ้นบล็อก
│   ├── GameEngine.java        - สถานะ 1 รอบ: กระดาน ถาด 3 ชิ้น คะแนน เกมจบ
│   ├── AuthService.java       - กติกา Sign up / Login
│   ├── PasswordHasher.java    - แฮชรหัสผ่าน
│   └── Session.java           - จำว่าใครล็อกอิน / ชื่อผู้เล่น
├── view/                      - หน้าจอทั้งหมด (Swing)
│   ├── GameFrame.java         - หน้าต่างหลัก สลับหน้าด้วย CardLayout
│   ├── LoginPanel.java        - หน้า Login
│   ├── SignUpPanel.java       - หน้า Sign up
│   ├── HomePanel.java         - เมนูหลัก
│   ├── GamePanel.java         - หน้าเล่นเกม (วาด + ลากวาง)
│   ├── LeaderboardPanel.java  - ตารางคะแนน
│   ├── PopupDialog.java       - คลาสแม่ของป๊อปอัพ
│   ├── SettingsDialog.java    - ป๊อปอัพตั้งค่า
│   ├── GameOverDialog.java    - ป๊อปอัพจบเกม
│   └── components/            - ชิ้นส่วน UI ใช้ซ้ำ (ปุ่ม, ช่องกรอก, โลโก้, Theme ...)
└── data/                      - การเก็บข้อมูลลงไฟล์
    ├── ScoreEntry.java        - ข้อมูลคะแนน 1 แถว
    ├── LeaderboardManager.java- โหลด/บันทึก blockblast_leaderboard.csv
    ├── User.java              - ข้อมูลผู้ใช้ 1 คน
    └── UserManager.java       - โหลด/บันทึก blockblast_users.csv
```

## การไหลของหน้าจอ
```
Login ── Sign up ──> Sign up ── สมัครสำเร็จ ──> Login
Login ── สำเร็จ ──> Home
Home ── Start ──> Game ── (จบเกม) ──> Game Over popup ── Play Again ──> Game ใหม่
                                                      └── Home ──────> Home (บันทึกคะแนนแล้ว)
Home ── Leaderboard ──> Leaderboard ── Home ──> Home
Game ── ⚙ ──> Settings popup ── X ──> เล่นต่อ / Home ──> Home (ไม่บันทึกรอบนี้)
Home ── Log out ──> Login
```
