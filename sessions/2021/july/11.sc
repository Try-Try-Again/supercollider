s.boot;
s.plotTree; // show the server's tree (it's like a dom for synths)

//  Basic PWM Osc
(SynthDef(\sound11_1, {
  var osc, pwm;
  pwm = SinOsc.kr(0.2).range(0.01, 0.99);
  osc = Pulse.ar(110, width: pwm);
  osc = Mix.new(osc)!2 * 0.1;
  Out.ar(0, osc);
}).add)

x = Synth.new(\sound11_1);
x.free;
