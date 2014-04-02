build.prop editor
=================

This tool is somewhat outdated, so I can nearly guarantee you that it'll break your configuration :(.

<h1>What is this tool used for?</h1>
This tool allows you to modify your build.prop file easily on your device. It makes editing easy and you don't have to take care about remounting stuff or read/write permissions. It also features descriptions which contain information about the property. A great tool for both: beginners and advanced users. It can be used to test a variety of properties on your device or even tweak it, but be warned: You've to know what you're doing! If the phone doesn't boot after a reboot, the initial backup of your device can be found at: /data/data/de.bwulfert.buildpropedit/build.prop

<h1>Descriptors & motivation</h1>
I've created this application because, obviously, I want to edit my build.prop file in a easy, non adb push / maybe remount, way. So I've decided to create this application which does these steps for you (remounting, backup your file, etc.) - you just have to download & install it, getting prompt to create a backup of your current build.prop file (it's just button clicking, nothing else) and you are good to go son! Hint: You actually have to create an initial backup otherwise the application closes it self. I'll add a neat "view" or "read" mode later but for the security of all our phones I think its the best deal for now.

After working on it for a while I noticed the big range of properties a build.prop file could contain - and which my knowledge doesn't. So I decided to create these descriptors which consists of:

    description - meaning of the property, what is it used for
    recommended values - alpha numerical values - e.g.: 0,1 or 180, 190, 200, 210 or dd-mm-yyyy, mm-dd-yyyy
    keyboard layouts - textual values - e.g.: NumBlock, CharacterInput or FileDialog (the FileDialog isn't implementet yet)

Of course I'm not able to describe all the properties by myself so I've spend some time searching on the net and came up with an amount of 24 descriptions. This isn't that much but a good start. Thats the point where I need help from anybody who's interested: If you want to contribute to this little project, you can contact me and sending me your descriptors (which are just plain-text xml files) - I'll merge them into the project. You can also help by translate this application into your language (english is still welcome, I am german) or donate a small amount.

<h2>XDA-Thread (contact, more informations)</h2>
http://forum.xda-developers.com/showthread.php?p=23529041
