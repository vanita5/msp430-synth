/*
 * Bob Somers 2011
 *   http://bobsomers.com
 *
 * Released under the Beerware License
 *   http://en.wikipedia.org/wiki/Beerware
 */

#ifndef PLAYER_H_
#define PLAYER_H_

#include "music.h"

/* Define speaker connections. */
#define SPEAKER BIT7
#define SPEAKER_DIR P1DIR
#define SPEAKER_OUT P1OUT

/*
 * Intialize the necessary peripherals.
 */
void init();

/*
 * CCR0 timer interrupt handler (note signal).
 */
//void __attribute__((interrupt (TIMERA0_VECTOR))) timer_sound_isr();

/*
 * TAIV timer interrupt handler (elapsed time counting).
 */
//void __attribute__((interrupt (TIMERA1_VECTOR))) timer_elapsed_isr();

/*
 * Program entry point.
 */
void main(void);

#endif // PLAYER_H_
