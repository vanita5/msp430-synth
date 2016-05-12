#include <msp430g2553.h>
#include <string.h>

#include "player.h"

#include "super_mario.h"

char string1[25];
unsigned int i;

volatile unsigned char received_note = 0;

void init() {
	/* Stop the watchdog. */
	WDTCTL = WDTPW + WDTHOLD;

	/* Set the system clock to 1 MHz. */
	DCOCTL = 0;
	BCSCTL1 = CALBC1_1MHZ;
	DCOCTL = CALDCO_1MHZ;

	/* Initialize speaker (output). */
	SPEAKER_DIR |= SPEAKER;

	/* Initialize the timer. */
	TACTL |= TASSEL_2 | ID_0 | MC_2 | TAIE;
	TACCTL0 |= CCIE;
	TACCTL1 |= CCIE;

	/* Initialize UART communication */
	P1SEL = BIT1 + BIT2; // P1.1 = RXD, P1.2=TXD
	P1SEL2 = BIT1 + BIT2;
	UCA0CTL1 |= UCSSEL_2; // SMCLK
	UCA0BR0 = 104; // 1MHz 9600
	UCA0BR1 = 0; // 1MHz 9600
	UCA0MCTL = UCBRS0; // Modulation UCBRSx = 1
	UCA0CTL1 &= ~UCSWRST; // **Initialize USCI state machine**
	IE2 |= UCA0RXIE; // Enable USCI_A0 RX interrupt



	/* Enable interrupts globally and enter LPM0 */
	//_bis_SR_register(LPM0_bits + GIE);
	_bis_SR_register(GIE);
}

#pragma vector=TIMER0_A0_VECTOR
__interrupt void TIMER_SOUND_ISR(void) {
	/* Push the timer forward by one note cycle. */
	TACCR0 += current_note;

	/* Output to the speaker if sound is enabled. */
	if (sound_enabled) {
		SPEAKER_OUT ^= SPEAKER;
	}
}

#pragma vector = TIMER0_A1_VECTOR
__interrupt void TIMER_ELAPSED_ISR(void) {
	switch (TAIV) {
	case 2: /* CCR1 */
		TACCR1 += 1000; /* 1 ms */
		ms_elapsed++;
		break;
	}
}

#pragma vector=USCIAB0TX_VECTOR
__interrupt void USCI0TX_ISR(void)
{

}

#pragma vector=USCIAB0RX_VECTOR
__interrupt void USCI0RX_ISR(void)
{
	received_note = (unsigned int)UCA0RXBUF;
}

void main(void) {
	/* Initialize the system. */
	init();
	set_bpm(127);

	while(1) {
		if (received_note == 0x00) {
			note_off();
		} else if (received_note > 0x00 && received_note <= 0x4A) { //0x01 - 0x4A
			note_on(NOTES_ARR[received_note]);
			//received_note = 0x00;
		} else if (received_note >= 0x4B && received_note < 0xFF){ // > 75
			set_bpm(received_note);
			received_note = 0x00;
		} else if (received_note == 0xFF) {
			song_setup();
			song_play();
			received_note = 0x00;
		}
	}
}
