s.boot;

// manual way

// old school electro S&H
(x = {
	var sig, freq, amp, reverb;
	freq = LFNoise0.ar(8!8).exprange(60, 1500).round(60);
	amp = VarSaw.ar(8, 0, 0.004).range(0, 1).pow(4);
	sig = LFTri.ar(freq);
	sig = sig * amp * 0.4;
	sig = Splay.ar(sig);
}.play(fadeTime:8);
)

s.makeWindow; // opens audio recording gui

// programmatic way
(
	Routine({
		//s.record;
		s.record("./electro.aif", duration:4);

		wait(0.02);

		x = {
			var sig, freq, amp, reverb;
			freq = LFNoise0.ar(8!8).exprange(60, 1500).round(60);
			amp = VarSaw.ar(8, 0, 0.004).range(0, 1).pow(4);
			sig = LFTri.ar(freq);
			sig = sig * amp * 0.4;
			sig = Splay.ar(sig);
		}.play(fadeTime:8);
	}).play;
)
x.release(1); // run these 2 commands to end recording
s.stopRecording;

// misc settins ( usually dont need to touch these )

s.recSampleFormat;
s.recHeaderFormat;
s.recChannels;

Platform.recordingDir;
