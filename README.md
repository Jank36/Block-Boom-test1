อธิบายการทำงานของแต่ละไฟล์ (แบบละเอียด)
ภาพรวมการทำงาน

โปรเจกต์แบ่งเป็น 3 ชั้น ชั้นล่างไม่รู้จักชั้นบน

data/ อ่านเขียนไฟล์ CSV
model/ เก็บกติกาและสถานะของเกม (ไม่มี UI) และเรียก data/ เมื่อต้องบันทึก
view/ วาดหน้าจอและรับเมาส์ แล้วเรียก model/ กับ data/

การสลับหน้าทั้งหมดผ่าน GameFrame ที่เดียว

Main.java

จุดเริ่มโปรแกรม มีบรรทัดเดียวคือ SwingUtilities.invokeLater(GameFrame::new) เพื่อสร้างหน้าต่างบน Event Dispatch Thread ตามข้อกำหนดของ Swing ไม่ได้อยู่ใน package ใด

model/ (กติกาเกม ไม่มี UI)
Piece.java

ชิ้นบล็อกหนึ่งชิ้น เก็บตำแหน่งช่องแบบสัมพัทธ์ int[][] cells เช่น {{0,0},{0,1},{1,0}} กับสี

constructor และ getCells() ทำสำเนาอาร์เรย์ (defensive copy) เพื่อไม่ให้ใครแก้ข้อมูลข้างในจากข้างนอกได้
getHeight() และ getWidth() คืนค่าตำแหน่งแถว/คอลัมน์ที่มากสุด + 1
size() คือจำนวนช่อง ใช้คิดคะแนน
PieceGenerator.java

โรงงานสร้างชิ้นบล็อก

เก็บรายการรูปร่างไว้ 22 แบบ (จุด, เส้น 2–5 ช่องทั้งแนวนอน/แนวตั้ง, สี่เหลี่ยม 2×2 และ 3×3, L เล็ก/ใหญ่, S/Z, T) กับสี 7 สี
generateRandom() สุ่มรูปร่างและสีแล้วสร้าง Piece
generateTray(count) สร้างหลายชิ้นเป็น List
Board.java

กระดาน 8×8 เก็บเป็น Color[][] (null คือช่องว่าง)

canPlace(piece, row, col) วนทุกช่องของชิ้น แล้วเช็กว่าไม่ล้นขอบและช่องปลายทางว่าง
place(...) ใส่สีลงกระดาน (ผู้เรียกต้องเช็ก canPlace ก่อน)
clearFullLines() ทำ 3 ขั้นตอน
หาแถวที่เต็ม
หาคอลัมน์ที่เต็ม
ล้างทั้งสองชุดพร้อมกัน
การหาก่อนแล้วค่อยล้างทำให้แถวกับคอลัมน์ที่ตัดกันถูกล้างครบทั้งคู่ คืนจำนวนเส้นที่ล้าง
hasNoValidMove(piece) คืน true ถ้าชิ้นนี้ไม่มีที่วางเลยบนกระดาน
reset() ล้างกระดาน
GameEngine.java

สถานะของหนึ่งรอบเล่น ถือ Board, PieceGenerator, ถาด 3 ชิ้น, คะแนน และ flag เกมจบ

newGame() รีเซ็ตกระดาน คะแนน และสุ่มถาดใหม่
canPlace(slot, row, col) คือเกมยังไม่จบ, ช่องถาดมีชิ้น และวางได้ตามกติกา
tryPlace(slot, row, col) เป็นหัวใจของเกม
เช็ก canPlace ถ้าไม่ผ่านคืน false
วางชิ้นลงกระดาน และบวกคะแนนเท่าจำนวนช่อง
เอาชิ้นออกจากถาด
เรียก clearFullLines() ถ้าล้างได้บวกโบนัส จำนวนเส้น × 10 × จำนวนเส้น (1 เส้น = 10, 2 เส้น = 40, 3 เส้น = 90)
ถ้าถาดว่างครบจะสุ่ม 3 ชิ้นใหม่
ตรวจเกมจบ คือไม่มีชิ้นใดเหลือที่วางได้เลย
getBoard(), getPiece(slot), getScore(), isGameOver() ให้ GamePanel อ่านไปวาด
AuthService.java

กติกาสมัครและล็อกอิน (static ทั้งหมด)

register(...) ตรวจตามลำดับนี้ และคืนข้อความ error ทันทีที่เจอ ถ้าผ่านหมดจะบันทึกและคืน null
ช่องว่าง
รูปแบบอีเมล (regex)
รหัสผ่านอย่างน้อย 4 ตัว
รหัสผ่านตรงกับช่องยืนยัน
username ซ้ำ
email ซ้ำ
login(identifier, password) หาผู้ใช้จาก username ก่อน ถ้าไม่เจอหาจาก email แล้วเทียบแฮชรหัสผ่าน คืน User หรือ null
PasswordHasher.java

hash(password) ต่อคำนำหน้า "blockblast:" แล้วทำ SHA-256 คืนเป็นสตริง hex เพื่อไม่เก็บรหัสผ่านจริงลงไฟล์ (ไม่มี salt แยกรายผู้ใช้ เหมาะกับงานเรียน ไม่ใช่ระบบจริง)

Session.java

ตัวถือสถานะว่าใครล็อกอินอยู่ (User) และ playerName ที่ใช้บน Leaderboard login(user) จะตั้งชื่อผู้เล่นเป็น username ให้อัตโนมัติ ส่วน logout() ล้างทั้งคู่ GameFrame ถือ instance เดียว

data/ (เก็บไฟล์)
ScoreEntry.java

ไฟล์เดิมของคุณ เก็บชื่อกับคะแนน (immutable)

LeaderboardManager.java

จัดการ blockblast_leaderboard.csv รูปแบบบรรทัดละ name,score

load() อ่านไฟล์แบบ UTF-8 แล้วแยกชื่อกับคะแนนด้วยคอมมาตัวสุดท้าย (ชื่อที่มีคอมมาก็ไม่พัง) จากนั้นรวมชื่อซ้ำ (ไม่สนตัวพิมพ์เล็กใหญ่) โดยเก็บคะแนนสูงสุด แล้วเรียงมากไปน้อย บรรทัดเสียจะถูกข้าม
addScore(name, score) โหลดรายการ ถ้ามีชื่อนี้แล้วและคะแนนใหม่สูงกว่าจะอัปเดต ถ้าไม่มีจะเพิ่ม แล้วเรียง ตัดเหลือ 10 อันดับ และเขียนทับไฟล์ ผู้เล่นที่หลุด 10 อันดับจะถูกลบจากไฟล์ด้วย
getTopScore() คะแนนสูงสุดของทุกคน (0 ถ้ายังไม่มีข้อมูล)
save(...) เขียนไฟล์ UTF-8 และเงียบไว้ถ้าเขียนไม่ได้ เพราะไม่กระทบการเล่น
User.java

ที่เก็บข้อมูลบัญชี ได้แก่ username, email, passwordHash

UserManager.java

จัดการ blockblast_users.csv บรรทัดละ username,email,hash

username กับ email ผ่าน URL-encode เพื่อให้ภาษาไทยและคอมมาปลอดภัย
add(user) ต่อท้ายไฟล์
findByUsername และ findByEmail โหลดทุกบรรทัดแล้วเทียบแบบไม่สนตัวพิมพ์ ไม่เจอคืน null
view/ (หน้าจอ)
GameFrame.java

หน้าต่างหลัก ถือ CardLayout และสร้างทั้ง 5 หน้าไว้ล่วงหน้า (Login, SignUp, Home, Game, Leaderboard) กับ Session

ตั้ง setResizable(false) ก่อน pack() ขนาดจึงมาจากหน้าจอ (390×610) แล้วเริ่มที่หน้า Login
เมธอดนำทาง
showLogin() / showSignUp() ล้างฟอร์มก่อนแสดง
logout() ล้าง session แล้วไป Login
goHome() เติมชื่อในช่อง Name ก่อนแสดง
startGame(name) บันทึกชื่อลง session, สั่ง gamePanel.startNewGame(name) แล้วสลับไปหน้าเกม
openLeaderboard() โหลดตารางใหม่ก่อนแสดง
LoginPanel.java

ช่อง Username/Email, Password, ข้อความ error สีแดง, ปุ่ม Login และลิงก์ไป Sign up โครงสร้างคือ GridBagLayout ที่จัดคอลัมน์ (BoxLayout แนวตั้ง) ไว้กึ่งกลาง ในคอลัมน์มีโลโก้ → การ์ดขาว → ช่องว่างยืด → ลิงก์ล่าง

doLogin() เรียก AuthService.login ถ้า null แสดง "Invalid username/email or password." ถ้าสำเร็จเก็บ user ลง session แล้ว goHome() กด Enter ที่ช่องรหัสผ่านก็ล็อกอินได้
reset() ล้างฟอร์มทุกครั้งที่กลับมาหน้านี้
SignUpPanel.java

โครงเหมือน Login แต่มี 4 ช่อง doRegister() เรียก AuthService.register ถ้าได้ข้อความ error แสดงใต้ฟอร์ม ถ้าสำเร็จขึ้น JOptionPane "Account created!" แล้วกลับ Login

HomePanel.java

เมนูหลัก ช่อง Name และปุ่ม 3 ปุ่ม

Start ตรวจว่าชื่อไม่ว่าง ถ้าว่างแสดงข้อความแดง ถ้าไม่ว่างเรียก frame.startGame(name)
Leaderboard เรียก openLeaderboard()
Log out เรียก logout()
refresh() เติม username ลงช่อง Name เป็นค่าตั้งต้น (แก้ได้) และล้าง error
GamePanel.java

หน้าเล่นเกม วาดด้วย paintComponent เอง และเป็น MouseListener กับ MouseMotionListener (สไตล์เดียวกับโค้ดเก่า) ค่าคงที่เลย์เอาต์ ได้แก่ ช่องละ 40px, กระดานกว้าง 320px, ขอบ 35px, กระดานเริ่มที่ y=100, ถาดเริ่มที่ y=450 สูง 120px, ช่องถาดกว้างช่องละ 106px

การวาด เรียงตามนี้

พื้นหลังไล่สีฟ้า
header (ป้าย Score สีม่วง, ป้าย Best Score สีเหลือง, ปุ่ม ⚙)
กระดาน (กรอบ, ช่องว่าง, บล็อกที่วางแล้ว, ไฮไลต์ตอนลาก)
ถาด 3 ช่อง
ชิ้นที่กำลังลาก (โปร่ง 85%)

การลาก

mousePressed ถ้าเกมจบไม่ทำอะไร ถ้าคลิกปุ่ม ⚙ เปิด SettingsDialog ถ้าคลิกในแถบถาด คำนวณช่องจาก (x - MARGIN) / TRAY_SLOT_W แล้วเริ่มลาก
mouseDragged อัปเดตตำแหน่งเมาส์ แล้วเรียก updateDragTarget() ซึ่งวางชิ้นให้กึ่งกลางอยู่ที่เมาส์ ปัดเป็นแถว/คอลัมน์บนกระดาน และเช็กด้วย engine.canPlace ช่องที่จะวางจะเป็นสีเขียวโปร่งถ้าวางได้ สีแดงถ้าวางไม่ได้
mouseReleased ถ้าตำแหน่งถูกต้องเรียก engine.tryPlace แล้วอัปเดต Best Score ถ้าเกมจบเรียก onGameOver()

จบเกม onGameOver() บันทึกคะแนนผ่าน LeaderboardManager.addScore, วาดหน้าใหม่ แล้วใช้ invokeLater เปิด GameOverDialog (เปิดหลังจบ event เมาส์ปัจจุบัน) ปุ่ม Play Again จะเรียก startNewGame เมธอด startNewGame(name) รีเซ็ต engine และโหลด Best Score ใหม่

LeaderboardPanel.java
header มีป้ายเหลือง "Leaderboard" และปุ่ม Home
ตารางเป็น JTable ที่ override prepareRenderer ให้แถว 1/2/3 เป็นสีทอง/เงิน/ทองแดง และแถวอื่นขาว (ใช้ flag hasData กันไม่ให้แถวข้อความ "ไม่มีข้อมูล" ถูกแต่งสีทอง) แล้วตั้ง renderer ให้ข้อความอยู่กึ่งกลาง หัวตารางสีกรมท่า ตัวขาว กรอบสีม่วง ปิดการแก้ไขและการเลือก
refresh() โหลดจาก LeaderboardManager ใส่ลำดับ 1..n ถ้าไม่มีข้อมูลแสดงแถว "No information available"
PopupDialog.java

คลาสแม่ของป๊อปอัพ (package-private) เป็น JDialog แบบ modal ไม่มีกรอบระบบ แถบหัวสีม่วงแสดงชื่อ ถ้า closable = true จะมีปุ่ม X สีแดงที่สั่ง dispose() ถ้าไม่ปิดได้จะใส่ช่องว่างถ่วงให้ชื่ออยู่กึ่งกลาง setBody(...) ใส่เนื้อหา สั่ง pack() และวางกึ่งกลางหน้าต่างเกม ความเป็น modal ทำให้เมาส์ไม่ไปถึงกระดานตอนป๊อปอัพเปิดอยู่ (เท่ากับพักเกม)

SettingsDialog.java

ป๊อปอัพ "Setting" ปิดได้ด้วย X มีปุ่ม Home สีแดง ซึ่งปิดป๊อปอัพแล้วเรียก frame.goHome() (ทิ้งรอบเล่นปัจจุบัน ไม่บันทึกคะแนน)

GameOverDialog.java

ป๊อปอัพ "Game Over" แสดงชื่อและ Score : N มีปุ่ม Play Again (ปิดแล้วเรียก callback onPlayAgain ที่ GamePanel ส่งมา) กับปุ่ม Home ไม่มีปุ่ม X เพื่อบังคับให้เลือกหนึ่งทาง

view/components/ (ชิ้นส่วน UI ใช้ซ้ำ)
Theme.java: ค่าสีกลางทั้งหมด (พื้นหลัง, ม่วง, เหลือง, เขียว, แดง, น้ำเงิน, สีช่องกระดาน ฯลฯ) และเมธอด font(style, size) ใช้ฟอนต์ Tahoma
GradientPanel.java: JPanel ที่วาดพื้นหลังไล่สีฟ้าบนลงล่าง ทุกหน้าสืบทอดจากตัวนี้
RoundedPanel.java: แผงมุมมนสีที่กำหนด (ใช้ทำการ์ดขาวและป้ายหัวตาราง)
RoundedButton.java: ปุ่มสไตล์เกม วาดสองชั้น (ชั้นล่างสีเข้มกว่าเป็นขอบนูน 4px) เมาส์ชี้แล้วสว่างขึ้น กดแล้วหน้าปุ่มจมลง
LinkButton.java: ปุ่มข้อความสีน้ำเงินไม่มีกรอบ สำหรับ "Sign up" / "Login"
FieldHint.java: ตัวช่วยกลางของช่องกรอก สร้างกรอบเส้นบาง + padding และวาดข้อความจางเมื่อช่องว่างและไม่ได้โฟกัส
PlaceholderField.java และ PlaceholderPasswordField.java: ช่องกรอกข้อความธรรมดาและช่องรหัสผ่านที่ใช้ FieldHint ในการวาดข้อความจาง
LogoLabel.java: วาดคำว่า Block (เหลือง) กับ Boom (ฟ้า) โดยแปลงตัวอักษรเป็นเส้นรอบรูป (glyph outline) ระบายสีพร้อมขอบกรมท่าหนา
Layouts.java: ตัวช่วยจัดแนวชิ้นส่วนใน BoxLayout ให้ชิดซ้ายเหมือนกันหมด เพราะ Swing ตั้งค่าแนวเริ่มต้นต่างกัน (ป้ายชิดซ้าย ปุ่มอยู่กลาง) จนหน้าเอียง
ไฟล์อื่น
run.sh: คอมไพล์ทุกไฟล์ลง out/ แล้วรัน Main
README.md: วิธีรัน โครงสร้างโฟลเดอร์ และผังการไหลของหน้าจอ
ข้อสังเกตเล็กน้อย

ใน GamePanel.mousePressed การคำนวณช่องถาดด้วย (x - MARGIN) / TRAY_SLOT_W ถ้ากดซ้ายของถาดในช่วง x ประมาณ 0–34px ในแถบถาด ผลหารจะปัดเป็น 0 และจะหยิบชิ้นช่องแรกได้ (โค้ดเก่าก็มีพฤติกรรมเดียวกัน) กระทบน้อยมาก ถ้าอยากให้แก้ บอกได้เลยครับ

ถ้าอยากให้เจาะลึกเป็นโค้ดทีละบรรทัดของไฟล์ไหน เช่น ลอจิกลากวางใน GamePanel หรือการวาดโลโก้ บอกได้เลยครับ
