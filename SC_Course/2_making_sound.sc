Server.local.boot; // starts audio server
s.boot // shorthand for starting audio server

Server.local.quit; // quits server
s.quit // shorthand for quitting audio server

{SinOsc.ar}.play; // play stuff between the brackets

x = {SinOsc.ar} // stores a function
x.play
x.free //sound will still play because a function cannot be free
y = x.play // stores a synth in a variable
y.free //free the synth (sound stops)

/*
'.play' creates a synth 'instance'. this is what is making noise
*/

{SinOsc.ar(330.0, 0.5, 0.5)}.play; //SinOsc.ar can take arguments (ctrl-D to find out what they are :)

{SinOsc.ar * 0.125}.play; // you can also mutiply the output like this

(
z = {
	arg freq=440, amp=1;
	var sig;
	sig = SinOsc.ar(freq) * amp;
}.play
)

z.set(\freq, 330); // arguments can be updated on the synth as it plays!
z.set(\amp, 0.5); // you can think of them as the input jacks on a module

z.set(\freq, 220, \amp, 0.7); // you can update as many arguments at once as you like!
// arguments must start with lowercase letters.q
z.free;

(
z = {
	arg noiseHz=8;
	var freq, amp, sig;
	//freq = LFNoise0.kr(8, 400, 600); //sample and hold source
	//freq = LFNoise0.kr(8).range(200, 1000); // this is a nicer way to do ranges with a ugen
	freq = LFNoise0.kr(noiseHz).exprange(200, 1000); // this uses exponential distribution - sounds better
	// Make sure not to use mul and add args with LFNoise if you're using .range as they will conflict
	amp = LFNoise1.kr(12).exprange(0.02, 1); //slow smooth random control for synths volume
	sig = SinOsc.ar(freq) * amp; // using the sample and hold source as an 'argument' for our synth.
	// Since the LFNoise0 is constantly updating itself, the argument keeps changing on the the synth!
}.play
)

z.set(\noiseHz, exprand(4, 64));

z.free;
