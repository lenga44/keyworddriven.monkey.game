# keyworddriven.monkey.game
1. When connecting appium inspector and device android, you meet an error message: ".WRITE_SECURE_SETTINGS java.lang.SecurityException: Permission denial: writing to settings", you need turn on mode "Disable Permission Monitoring" in Developer option
2. Get activity name: adb shell "dumpsys activity activities | grep mResumedActivity"
3. adb forward tcp:8342 tcp:8342