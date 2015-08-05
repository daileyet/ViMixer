# ViMixerApp


The quick encryption lib and tool for image and video.

## ViMixerLib

The lib for encrypting files(image,video etc.) by [AES][1].

## ViMixerUI

The UI for encrypting image and video files by configuration XML file.

There are two types configuration, one is smart and another is simaple.

BTW, the UI is implemented by JavaFX, required Java 8 version.

### Usage

Before you start encrypting your files, you need make a little configuration for this app.

In ``Configure Panel``, setting a key to protected your encrypting on files. This is very important, you must remember it; You only can get the right decrypting file by using the same key during entrypting.

The configuration item ``Segment Type`` only support two values now. 

#### Smart Segment
It is the recommended type for configuration. Because, it is so fast to encrypt the file blocks.

It will not encrypt all the file blocks, it will get the file blocks by a quick and easy strategy and only encrypt those blocks.

It is very suitable for those huge files, like vedio and HD picture. 

#### Simple Segment
This type of configuration will be slow during encrypting.
It will encrypt most of the file blocks, of course, we can configure it details.

So, it is used when you need keep your file higher security.

### Screenshots
![start window][2]
![adding file][3]
![blocks show][4]
![smart conf][5]
![smart conf - blocks][6]
![simple conf - progress][7]
![blocks dynamic][8]
![progress dynamic][9]
![file status][10]
![localization][11]

 [1]: https://en.wikipedia.org/wiki/Advanced_Encryption_Standard
 [2]: https://github.com/daileyet/ViMixer/blob/master/screenshots/Snap01.png
 [3]: https://github.com/daileyet/ViMixer/blob/master/screenshots/Snap02.png
 [4]: https://github.com/daileyet/ViMixer/blob/master/screenshots/Snap03.png
 [5]: https://github.com/daileyet/ViMixer/blob/master/screenshots/Snap04.png
 [6]: https://github.com/daileyet/ViMixer/blob/master/screenshots/Snap05.png
 [7]: https://github.com/daileyet/ViMixer/blob/master/screenshots/Snap06.png
 [8]: https://github.com/daileyet/ViMixer/blob/master/screenshots/Snap07.png
 [9]: https://github.com/daileyet/ViMixer/blob/master/screenshots/Snap08.png
 [10]: https://github.com/daileyet/ViMixer/blob/master/screenshots/Snap09.png
 [11]: https://github.com/daileyet/ViMixer/blob/master/screenshots/Snap10.png
