//"Hello, World!".postln
s.boot;
x = 3.cubed;
f = { [SinOsc.ar(440, 0, 0.2), SinOsc.ar(442, 0, 0.2)] };
f.play;
var mathString = { "Evaluating...".postln; 2 + 3 };
mathString; //Why doesn't this work???
