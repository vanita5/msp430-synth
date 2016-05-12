/*
 * Sergio Campama 2011
 *   http://kaipi.me
 *
 * Released under the Beerware License
 *   http://en.wikipedia.org/wiki/Beerware
 *
 * Super Mario Theme
 */

/*
 * ALL SONGS MUST IMPLEMENT song_setup() AND song_play()!
 */

void song_setup() {
}

void song_measure_1() {
    play(NOTES_ARR[29], 1);
    play(NOTES_ARR[29], 1);
    rest(1);
    play(NOTES_ARR[29], 1);
    rest(1);
    play(NOTES_ARR[25], 1);
    play(NOTES_ARR[29], 1);
    rest(1);
    play(NOTES_ARR[32], 1);
    rest(3);
    play(NOTES_ARR[20], 1);
    rest(3);
}

void song_measure_2() {
    play(NOTES_ARR[25], 1);
    rest(2);
    play(NOTES_ARR[20], 1);
    rest(2);
    play(NOTES_ARR[17], 1);
    rest(2);
    play(NOTES_ARR[22], 1);
    rest(1);
    play(NOTES_ARR[24], 1);
    rest(1);
    play(NOTES_ARR[23], 1);
    play(NOTES_ARR[22], 1);
    rest(1);
}

void song_measure_3() {
    play(NOTES_ARR[20], 1);
    play(NOTES_ARR[29], 1);
    rest(1);
    play(NOTES_ARR[32], 1);
    play(NOTES_ARR[34], 1);
    rest(1);
    play(NOTES_ARR[30], 1);
    play(NOTES_ARR[32], 1);
    rest(1);
    play(NOTES_ARR[29], 1);
    rest(1);
    play(NOTES_ARR[25], 1);
    play(NOTES_ARR[27], 1);
    play(NOTES_ARR[24], 1);
    rest(2);
}

void song_measure_4() {
    rest(2);
    play(NOTES_ARR[32], 1);
    play(NOTES_ARR[31], 1);
    play(NOTES_ARR[30],1);
    play(NOTES_ARR[28], 1);
    rest(1);
    play(NOTES_ARR[29], 1);
    rest(1);
    play(NOTES_ARR[21], 1);
    play(NOTES_ARR[22], 1);
    play(NOTES_ARR[25], 1);
    rest(1);
    play(NOTES_ARR[22], 1);
    play(NOTES_ARR[25], 1);
    play(NOTES_ARR[27], 1);
}

void song_measure_5() {
    rest(2);
    play(NOTES_ARR[32], 1);
    play(NOTES_ARR[31], 1);
    play(NOTES_ARR[30],1);
    play(NOTES_ARR[28], 1);
    rest(1);
    play(NOTES_ARR[29], 1);
    rest(1);
    play(NOTES_ARR[37], 1);
    rest(1);
    play(NOTES_ARR[37], 1);
    play(NOTES_ARR[37], 1);
    rest(3);
}

void song_measure_6() {
    rest(2);
    play(NOTES_ARR[28], 1);
    rest(2);
    play(NOTES_ARR[27], 1);
    rest(2);
    play(NOTES_ARR[25], 1);
    rest(7);
}

void song_measure_7() {
    play(NOTES_ARR[25], 1);
    play(NOTES_ARR[25], 1);
    rest(1);
    play(NOTES_ARR[25], 1);
    rest(1);
    play(NOTES_ARR[25], 1);
    play(NOTES_ARR[27], 1);
    rest(1);
    play(NOTES_ARR[29], 1);
    play(NOTES_ARR[25], 1);
    rest(1);
    play(NOTES_ARR[22], 1);
    play(NOTES_ARR[20], 1);
    rest(3);
}

void song_measure_8() {
    play(NOTES_ARR[25], 1);
    play(NOTES_ARR[25], 1);
    rest(1);
    play(NOTES_ARR[25], 1);
    rest(1);
    play(NOTES_ARR[25], 1);
    play(NOTES_ARR[27], 1);
    play(NOTES_ARR[29],1);
    rest(7);
}

void song_play() {
    song_measure_1();
    song_measure_2();
    song_measure_3();
    song_measure_2();
    song_measure_3();
    song_measure_4();
    song_measure_5();
    song_measure_4();
    song_measure_6();

//    song_measure_4();
//    song_measure_5();
//    song_measure_4();
//    song_measure_6();
//    song_measure_7();
//    song_measure_8();
//    song_measure_7();
}
