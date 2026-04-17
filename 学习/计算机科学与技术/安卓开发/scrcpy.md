GitHub 开源。
##### 无线模式
1. USB调试模式连接，开启 ADB TCP 模式：adb tcpip 5555
2. 无线模式连接：adb connect 手机IP:5555
	```C
	// Android 默认热点网关：192.168.43.1
	adb connect 192.168.43.1:5555
	```
3. 启动 scrcpy
##### 操作
- 唤醒屏幕：鼠标右键
##### 命令
- 查看 scrcpy 版本：`scrcpy --version`
- 查看手机摄像头：`scrcpy --list-cameras`
- 自动录像 scrcpy 传输画面保存到电脑：`--record=my_video.mkv`
- 采集手机摄像头：`scrcpy --video-source=camera --camera-id=0 --camera-size=1920x1080 --camera-fps=30`
	- `--video-source=camera`：指定 scrcpy 采集手机摄像头
	- `--camera-id=0`：指定采集手机摄像头 ID 为 0 的摄像头（缺省此命令时的默认配置）
	- `--camera-size`：指定摄像分辨率
	- `--camera-fps=30`：指定帧率
	- `--no-audio`：不采集手机音频
#### 脚本
##### 远程熄屏控制
远程控制手机（需要先开启 ADB TCP 模式）并保持手机熄屏。
```C
Set shell = CreateObject("Wscript.Shell")

shell.Run "cmd /c adb connect 192.168.43.1:5555", 0, true

Do
    Set exec = shell.Exec("cmd /c adb devices")
    output = exec.StdOut.ReadAll

    If InStr(output, "192.168.43.1:5555") > 0 And InStr(output, "device") > 0 Then
        Exit Do
    End If

    WScript.Sleep 1000
Loop

strCommand = "cmd /c scrcpy.exe --turn-screen-off --power-off-on-close"

For Each Arg In WScript.Arguments
    strCommand = strCommand & " """ & Replace(Arg, """", """""""""") & """"
Next

shell.Run strCommand, 0, false
```