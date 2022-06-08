# HipHopHeads

HipHopHeads is an Android app that helps you keep track of new Hip Hop music releases. Features include providing links to major streaming services, allowing you to save releases to revist later, and getting notifications when your favorite artists release music.

<img src="https://i.imgur.com/JmqjSGi.png" width="400"/>  <img src="https://i.imgur.com/tZATgHr.png" width = "394" />

Technologies Employed -
* Kotlin
* MVVM
* Retrofit
* Room

The HipHopHeads app works via crowdsourcing music releases. It does this by using Retrofit to read posts from the HipHopHeads forum on Reddit. When new music is released, users will tag the post as [FRESH], indicating that a new release has come out. The app then scans the post and its comments for links to the new music on various streaming platforms.
