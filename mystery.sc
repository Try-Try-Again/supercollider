s.boot;
(
  Pbind(
    \freq, Pn(Pseries(110, 111, 10), 2),
    \dur, 1/2,
    \legato, Pwhite(0.1, 1)
  ).play;
    Pbind(
    \freq, Pn(Pseries(220, 222, 10), 4),
    \dur, 1/4,
    \legato, Pwhite(0.1, 1)
  ).play;
  Pbind(
    \freq, Pn(Pseries(330, 333, 10), 6),
    \dur, 1/6,
    \legato, 0.1
  ).play;
)
