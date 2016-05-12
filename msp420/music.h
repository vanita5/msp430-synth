/*
 * music.h
 *
 *  Created on: 14.02.2015
 *      Author: Eliah
 */

#ifndef MUSIC_H_
#define MUSIC_H_

/* The number of milliseconds to kill the note before the duration expires.
 * This leaves a nice gap between back-to-back notes. */
#define DEAD_TIME_MS 20

/* Assuming 4/4 time, this is how many ticks we subdivide the beat into. So
 * in other words, 4 ticks per beat gives us 16th note resolution. We then
 * define all note durations in lengths of these 16th note "ticks". */
#define TICKS_PER_BEAT 4

/* Note definitions, calculated with 1,000,000 Hz / note frequency / 2.
 * TODO: Add more notes as necessary. */

static const int NOTES_ARR[] = {
		0,			//none
		7645,		//C2 	1
		7215,		//Cs2 	2
		6810,		//D2 	3
		6428,		//Ds2 	4
		6067, 		//E2 	5
		5727,		//F2 	6
		5405,		//Fs2 	7
		5102,		//G2 	8
		4816,		//Gs2 	9
		4545,		//A2 	10
		4290,		//As2 	11
		4050,		//H2 	12

		3822,		//C3 	13
		3608,		//Cs3 	14
		3405,		//D3 	15
		3214,		//Ds3 	16
		3034,		//E3 	17
		2863,		//F3 	18
		2703,		//Fs3 	19
		2551,		//G3 	20
		2408,		//Gs3 	21
		2273,		//A3 	22
		2145,		//As3 	23
		2025,		//H3 	24

		1911,		//C4	25
		1804,		//Cs4	26
		1703,		//D4	27
		1607,		//Ds4	28
		1517,		//E4	29
		1432,		//F4	30
		1351,		//Fs4	31
		1276,		//G4	32
		1204,		//Gs4	33
		1136,		//A4	34
		1073,		//As4	35
		1012,		//H4	36

		956,		//C5	37
		902,		//Cs5	38
		851,		//D5	39
		804,		//Ds5	40
		758,		//E5	41
		716,		//F5	42
		676,		//Fs5	43
		637,		//G5	44
		601,		//Gs5	45
		568,		//A5	46
		536,		//As5	47
		506,		//H5	48

		478,		//C6	49
		451,		//Cs6	50
		426,		//D6	51
		402,		//Ds6	52
		379,		//E6	53
		358,		//F6	54
		338,		//Fs6	55
		319,		//G6	56
		301,		//Gs6	57
		284,		//A6	58
		268,		//As6	59
		253,		//H6	60

		239,		//C7	61
		225,		//Cs7	62
		213,		//D7	63
		201,		//Ds7	64
		190,		//E7	65
		179,		//F7	66
		169,		//Fs7	67
		159,		//G7	68
		150,		//Gs7	69
		142,		//A7	70
		134,		//As7	71
		127,		//H7	72
		0
};

//#define C4 1911
//#define D4 1703
//
//
//#define E3 3033
//#define Fs3 2703
//#define G3 2551
//#define Gs3 2408
//#define A3 2273
//#define As3 2145
//#define B3 2025
//#define Cb4
//#define Cs4 1804
//#define Db4 1804
//#define Ds4 1607
//#define Eb4 1607
//#define E4 1517
//#define F4 1432
//#define Fs4 1351
//#define Gb4 1351
//#define G4 1276
//#define Gs4 1204
//#define Ab4 1204
//#define A4 1136
//#define As4 1073
//#define Bb4 1073
//#define B4 1012
//#define C5 956
//#define Cs5 902
//#define Db5 902
//#define D5 851
//#define Ds5 804
//#define Eb5 804
//#define E5 758
//#define F5 716
//#define Fs5 676
//#define Gb5 676
//#define G5 638
//#define Gs5 602
//#define Ab5 602
//#define A5 568

/* Turns the sound output on or off. */
extern volatile unsigned int sound_enabled;

/* Keeps track of the current note we're playing. */
extern volatile unsigned int current_note;

/* Keeps track of the number of ms elapsed. */
extern volatile unsigned int ms_elapsed;

/*
 * Sets the tempo in BPM (beats per minute) at which the music will play back.
 */
void set_bpm(unsigned int bpm);

/*
 * Plays the passed note for the given duration (in ticks, see TICKS PER BEAT).
 * Blocks until the note is over.
 */
void play(unsigned int note, unsigned int duration_ticks);

/*
 * Plays the passed note
 */
void note_on(unsigned int note);

/*
 *
 */
void note_off();

/*
 * Rests for the given duration. Exactly the same as playing, except we just
 * don't play a note for the duration.
 */
void rest(unsigned int duration_ticks);


#endif /* MUSIC_H_ */
