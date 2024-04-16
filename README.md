# keyworddriven.monkey.game
1. When connecting appium inspector and device android, you meet an error message: ".WRITE_SECURE_SETTINGS java.lang.SecurityException: Permission denial: writing to settings", you need turn on mode "Disable Permission Monitoring" in Developer option
2. Get activity name: adb shell "dumpsys activity activities | grep mResumedActivity"
3. adb forward tcp:8342 tcp:8342
4. Build java file "mvn clean package -DskipTests"
5. run test "java -jar keyworddriven.monkey.game-2.0-SNAPSHOT-fat-tests.jar"
4. Hướng dẫn sử dụng:
 + B1: Kiểm tra thư mực chạy test bao gồm: 1 file java để chạy và 1 folder "config" (log, testcase)
 + B2: Kiểm tra folder testcase sẽ bao gồm 1 file excel là scope: file scope sẽ lưu tên các file testcase, và những file testcase còn lại
 + B3: Để run cần kết nối điện thoại và đổi port trên đt sang 8342
>> Lưu ý:
> 
    + Không đổi tên file logging, file config
    + Đổi tên file scope đổi trong file config
    + Để đúng tên file testcase trong file scope và lưu trong đường dẫn trong file config
    + Không thay đổi cấu trúc trong file scope và testcase
    + Sau khi chạy hoàn thành kết quả trả về file testcase tương ứng
    + Bắt đầu chạy sẽ restet file log và file testcase
    + Nên tránh để ổ C và để trực tiếp vào ổ viduj E:\tool_test_game