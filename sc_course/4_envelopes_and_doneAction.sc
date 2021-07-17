s.boot;
s.plotTree; // show the server's tree (it's like a dom for synths)
x = {PinkNoise.ar * 0.5}.play; //simple pink noise gen
x.free;

(y = {
  var sig, env, freq;
  // env = Line.kr(1, 0, 1); // after the env is done the synth is still here
  env = Line.kr(1, 0, 1, doneAction:2); // doneAction 2 will free synth
  // env = XLine.kr(1, 0.01, 1, doneAction:2); // exponential envelope
  // it's mathmatically impossible to interpolate exponentially
  // when including or crossing zero in the output range
  //sig = Pulse.ar(ExpRand(30, 500)) * env;
  // exp envelopes are great for pitch control too!
  freq = XLine.kr(880, 110, 1, doneAction:2);
  // you can also converto from lin to exp with `env.dbamp`
  sig = Pulse.ar(freq) * env.dbamp * 0.2;
  /*
  NOTE:
  if you have a synth with multiple UGens with a doneAction 2,
  the first UGen to finish will free the synth. It won't wait for the
  slower UGens to finish.
  */
}.play)

s.freeAll // another way to clear synths (like ^.)


// you can make envs more complex than lines with Env
({
  var sig, env;
  env = EnvGen.kr(Env.new, doneAction: 2); // creates default triangle env
  sig = Pulse.ar(ExpRand(30, 500)) * env;
}.play)
//
Env.new(
  // make sure not to touch zero with exp!
  [0.01, 1, 0.2, 0.01], // levels
  [0.5, 1, 2], // times
  //[5, -3, 8] // curve
  //\exp // you can use symbols for curves in place of an array!
  [\sine, -3, \lin] // or even mix symbols and numbers in array!
).plot; // you can visualize an envelope with `.plot`

// above example with custom envelope
({
  var sig, env;
  env = EnvGen.kr(
    Env.new([0.01, 1, 0.2, 0.01], [0.5, 1, 2], [\sine, -3, \lin]),
    doneAction: 2);
    sig = Pulse.ar(ExpRand(30, 500)) * env;
  }.play;
)

// env gen's second argument is optiona gate
(x = {
  //arg gate=0; // this has to be set back to zero before triggering again
  //arg t_gate=0; // `t_` prefixed arguments reset themselves to zero
  arg t_gate=1; // t gates defaulting to 1 will fire on creation (retrigs too!)
  var sig, env;
  env = EnvGen.kr(
    Env.new(
      [0, 1, 0.2, 0],
      [0.5, 1, 2],
      [3, -3, 0]),
      t_gate
    );
    sig = Pulse.ar(LFPulse.kr(8).range(600, 900)) * env;
  }.play;
)
x.set(\t_gate, 1);
/*
NOTE:
a synth containing UGens with doneAction: 2 are retriggerable
as long as the UGen does not finish.
*/


// you can do note-on/note-off style envs like this:

(y = {
  arg gate=0;
  var sig, env, freq;
  freq = EnvGen.kr(Env.adsr(1), gate, 200, 0.1); //using adsr for freq too
  env = EnvGen.kr(Env.adsr, gate); // read more about adsr
  sig = VarSaw.ar(SinOsc.kr(freq).range(500, 1000)) * env;
}.play;
)

y.set(\gate, 0);
