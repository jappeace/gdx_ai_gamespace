# AI Game Space

This is a little hobby project of mine in which I create a space to study
AI behaviour.

## Progress
As of right now its basically useless. It starts, you can place some
units and then press start to move them around.

However I poured quite a lot of work
in it already so I decided to put it on github as a backup.

## Planned features

1. Networking.
2. Some sort of building cost
3. Fighting
4. Autonomous AI (this will use the networking protocol)

After implementing all these things I will call it a version 1 and it will
be somewhat useful and there will be some fun.

I'll do networking first because I think its the most difficult to do. So it
may affect the design most. It'll also offer a good template for which the
AI can speak against. Assuming the AI has its own thread so it sort off just
plays like another player in real time. Getting copies of the current state
and making decisions based on those copies (this is what your eyes are doing
when you see the game).

## Architecture
The architecture will be as functional as is possible IMHO. But I won't
go out of my way to ensure architecture. I do revisit code when I think
it can be done better. But I always do that.

## License
The license is GPLv3. Feel free to use the code in anyway you can under
the terms of that license.
