# MSP430 Synth // MIDI Player

This was a school project I did in 2015.
The code base is kinda dirty and just released as-is.

## Example (Vine - click on it!)

[![Vine](https://v.cdn.vine.co/r/thumbs/0C46B3547A1202662425691086848_SW_WEBM_1429711278164_image.jpg?versionId=5ujcU5px3k7pT7ZUJPWBBPcL.SWKXkL0)](https://vine.co/v/eaH9ha2bZLz/embed/simple "MSP430 Synth")

## About

### msp430

This directory contains the code running on my MSP430 by Texas Instruments.
It receives commands over a USB (serial) connection from the desktop client.

Values in the range 0x01 - 0x4A are interpreted as notes from C2 up to H7.
You can also test the basic functionality with [screen](https://www.gnu.org/software/screen/manual/screen.html). See the [video](https://vine.co/v/O2phYbQHJdE)!

You might need [Code Composer Studio](http://www.ti.com/ww/en/launchpad/software.html) from Texas Instruments to compile.


### desktop

This is the Java (Swing) desktop GUI and a MIDI device bridge to control the MSP430.

It allows you to connect to the MSP430 and play the piano on the screen. The sound is generated on the MSP430.
You are also able to connect to a MIDI keyboard and other MIDI devices and play with them.



## Notes

This was just a small school project and I didn't have that much time. I could've done much more out of this.

For example, I started defining notes from C2 up to H7 on both, the MSP430 and the desktop GUI and map them. That's pretty much redundant. You could simply calculate required values.

Also it's not possible to play more than one note at a time. It'd be really great if you could play chords with this.


## License and stuff

This contains modified code from Bob Somers: [Click](https://github.com/bobsomers/msp430-launchpad-music)

Licensed under the [Beerware License](https://en.wikipedia.org/wiki/Beerware).