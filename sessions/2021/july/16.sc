s.boot;

//polyphonic stab
(SynthDef(\pulse_osc, {
	var osc, pwm, amp_env;
	amp_env = EnvGen.kr(
		Env.new([1, 0.01], [0.5, 1, 2], [\sine, -3, \lin]),
		doneAction: 2
	);
	pwm = SinOsc.kr(0.2).range(0.01, 0.99);
	osc = Pulse.ar(70, width: pwm);
	osc = Mix.new(osc)!2 * amp_env * 0.1;
	Out.ar(0, osc);
}).add;
)

x = Synth.new(\pulse_osc);
x.free;
