```shell
# 挂载DMG安装映像
hdiutil attach /Applications/Install\ macOS\ Sierra.app/Contents/SharedSupport/InstallESD.dmg -noverify -nobrowse -mountpoint /Volumes/install_app

# 创建并挂载空白映像
hdiutil create -o /tmp/MacOSInstall.cdr -size 7316m -layout SPUD -fs HFS+J
hdiutil attach /tmp/MacOSInstall.cdr.dmg -noverify -nobrowse -mountpoint /Volumes/install_build

# 将BaseSystem.dmg恢复到空白映像
asr restore -source /Volumes/install_app/BaseSystem.dmg -target /Volumes/install_build -noprompt -noverify -erase

# 删除链接并复制安装文件到空白映像
rm /Volumes/OS\ X\ Base\ System/System/Installation/Packages
cp -rp /Volumes/install_app/Packages /Volumes/OS\ X\ Base\ System/System/Installation/
cp -rp /Volumes/install_app/BaseSystem.chunklist /Volumes/OS\ X\ Base\ System/BaseSystem.chunklist
cp -rp /Volumes/install_app/BaseSystem.dmg /Volumes/OS\ X\ Base\ System/BaseSystem.dmg

# 卸载映像并转换ISO文件
hdiutil detach /Volumes/install_app
hdiutil detach /Volumes/OS\ X\ Base\ System/
hdiutil convert /tmp/MacOSInstall.cdr.dmg -format UDTO -o ~/Desktop/MacOSInstall.iso
mv /Desktop/MacOSInstall.iso.cdr ~/Desktop/MacOSInstall.iso
rm /tmp/MacOSInstall.cdr.dmg
```

