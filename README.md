# AI Game Space

This is a little hobby project of mine in which I create a space to study
AI behaviour.

## Progress
As of right now its basically useless. It starts, you can place some
units and then press start to move them around.

However I poured quite a lot of work
in it already so I decided to put it on github as a backup.

## Planned features

1. Teams, currently everyone controlls the same stuff
2. Some sort of building cost
3. Fighting
4. Autonomous AI (this will use the networking protocol)
5. networking speed:
	1. Udp networking for sending the world
	2. Tiled world where only the relevant tiles get send trough the network

After implementing all these things I will call it a version 1 and it will
be somewhat useful and there will be some fun.

# Features:

0. Basic motion of units and rendering
1. UI
2. Networking, Uses the akka networking

## Architecture
The architecture will be as functional as is possible IMHO. But I won't
go out of my way to ensure architecture. I do revisit code when I think
it can be done better. But I always do that.

### Networking
The networking is currently implemented in the most stupid way possible.
The entire world gets send every update tick.

The updateactor is in charge of the world. He owns it, all the clients just
get updates every gametick. The gameticks are received from the host.

the networking also still uses tcp so this is probably to slow. Using UDP for this makes
a lot more sense. (since in reality it doesn't matter if a packet or 2
gets dropped).
However for the incomming commands to the update actor TCP should be used.

## License
The license is GPLv3. Feel free to use the code in anyway you can under
the terms of that license.
