s.boot;
s.meter;

/*
you can add an array of UGens to a function and it will distribute
each UGen accross any available output channels.
currently, we only have 2 outputs configured.
figure out what happens if we add more UGens than channels available
*/

x = {[SinOsc.ar(300), SinOsc.ar(500)]}.play;

/*
another way to create multiple SinOscs is to put an array
of arguments into a single SinOsc
*/

x = {SinOsc.ar([300, 500])}.play;

(x = {
  var sig, amp;
  /*
  Here we have amp being assigned an array of control UGens
  each one has a different rate.
  */
  amp = SinOsc.kr([7, 1]).range(0, 1);
  /*
  Just like above, sig is an array of audio rate UGens
  */
  sig = SinOsc.ar([300, 500]);
  /*
  When sig (an array of UGens) is multiplies by amp (another array of UGens)
  element 1 of sig is mulitplied by element 1 of amp
  and element 2 of sig is mulitplied by element 2 of amp
  */
  sig = sig * amp;
}.play;
)
/*
If we multiply 2 multichannel signals with different array lenghs
the number of channels used will be the length of the larger array
the when the smaller array is used up, it will wrap back to the first
element:
[1, 2, 3, 4] * [5, 2, 3] => [5, 4, 9, 20]
*/
(x = {
  var sig, amp;
  amp = SinOsc.kr([7, 1, 2, 0.2, 6]).range(0.1);
  sig = SinOsc.ar([300, 500, 700, 900, 1100]);
  sig = sig * amp;
  //Mix.new(sig) * 0.25; //mixes channels down to 1 output
  //[Mix.new(sig), Mix.new(sig)] * 0.25; //dupliace signal for stereo
  //Mix.new(sig).dup(2) * 0.25; // same as above with dup method
  //Mix.new(sig)!2 * 0.25; // shorthand for dup
  /*
  Splay will split up arbitrary number of ugens accross stereo field
  more complex sound than Mix
  */
  Splay.ar(sig) * 0.5;
}.play
)

x = {PinkNoise.ar(0.5)!2}.play; //exact same audio in both channels
x = {PinkNoise.ar(0.5!2)}.play; //each channel is unique

(
  SynthDef.new(\multi, {
    var sig, amp;
    amp = SinOsc.kr([7, 1, 2, 0.2, 6]).range(0,1);
    sig = SinOsc.ar([300, 500, 700, 900, 1100]);
    sig = sig * amp;
    Splay.ar(sig) * 0.5;
    //What do we supply for the bus arg for Out.ar()?
    Out.ar(0, sig);
    /*
    Out.ar([0, 1], sig);
    this looks reasonable, but it results in unexpected behavior
    This results in multi-channel expansino on a multi channel signal
    this results in a third signal and channel1 (2 for normies) is
    loud because it contains 2 interfering signals
    */
  }).add;
)

x = Synth.new(\multi);

rrand(50, 1200)!4;
/*
results in:
-> [ 178, 178, 178, 178 ]
-> [ 745, 745, 745, 745 ]
-> [ 1126, 1126, 1126, 1126 ]
*/
//wrap in curly braces to become function
{rrand(50, 1200)}!4;
/*
function is evaluated 4 times for 4 different numbers
-> [ 387, 222, 1037, 1159 ]
-> [ 532, 688, 488, 223 ]
-> [ 842, 157, 822, 392 ]
*/

(
  SynthDef.new(\randomMulti, {
    var sig, amp, env;
    env = EnvGen.kr(
      Env.new([0, 1, 0], [10, 10], [1, -1]), doneAction: 2);
      // without the curly braces, all the signals would be the same
      //amp = SinOsc.kr({exprand(0.2,12)}!8).range(0, 1);
      //sig = SinOsc.ar({exprand(30, 1200)}!8);
      /*
      Lowercase exprand is not ideal because it's evaluated during the
      synth definition. Every time you create a new synth it will
      sound the same as the last one.
      */
      // Uppercase ExpRand is evaluated when synth is created (not defined)
      amp = SinOsc.kr({ExpRand(0.2,12)}!8).range(0, 1);
      sig = SinOsc.ar({ExpRand(30, 1200)}!8);
      sig = sig * amp * env;
      sig = Splay.ar(sig) * 0.5;
      Out.ar(0, sig);
    }).add;
  )

  y = Synth.new(\randomMulti);
  y.free;
