# Group Project - *SuperMeetup*

**SuperMeetup** is an android app that allows a user to search meetups in the world, to see new meetups on time, and to get recommended meetups. The app utilizes [Meetup REST API](https://www.meetup.com/meetup_api/).

Time spent: 80 hours (two people) spent in total

## User Stories

Incorporates 4 mobile-oriented features: maps, location, sensors, and real-time.

The following **required** functionality is completed:

* [x] Build data model and app structure.
* [x] User can log in to get authentication.
* [x] User can [get meetup categorys](https://www.meetup.com/meetup_api/docs/find/topic_categories/)
* [x] User can [find meetups](https://www.meetup.com/meetup_api/docs/find/events/)
* [x] User can [get recommended meetups](https://www.meetup.com/meetup_api/docs/recommended/events/)
* [x] User can subscribe [real time open meetups](https://www.meetup.com/meetup_api/docs/stream/2/open_events/)
* [x] User can [get user's profile](https://www.meetup.com/meetup_api/docs/members/:member_id/#get)
* [x] User can [get meetup details](https://www.meetup.com/meetup_api/docs/:urlname/events/:id/#get)
* [x] User can [get meetup group details](https://www.meetup.com/meetup_api/docs/:urlname/#get)
* [x] User can [get meetups in a group](https://www.meetup.com/meetup_api/docs/:urlname/events/#list)
* [x] User can switch Nearby, Search, New and Shake UI using BottomNavigationView.
* [x] Nearby UI: Show nearby meetups
  * [x] Show recommend categories
  * [x] Show nearby meetups in each category
* [x] Search UI: User can search meetups. 
  * [x] Show search result in a list by default 
  * [x] and can be switch to mapview
* [x] New UI: User can see real-time new meetups in mapview. Get real-time new meetups via Meetup Stream API
* [x] Shake UI: User can shake phone to get a recommended meetup.
  * [x] Show user's profile include interests list and membership
  * [x] Use interests information in user's profile to select a recommended meetup
* [x] Click any meetup item, user can see the meetup detail UI
  * [x] Show meetup's group info and event host info (if it has)
* [x] Click meetup's group info to show group detail UI
  * [x] List sample participant's profile image in each group detail ui
  * [x] List past events in each group detail ui
* [x] User can [infinitely paginate](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView) any listview.

## Wireframing

Here's a walkthrough of implemented user stories:
[Video](https://github.com/super-meetup/SuperMeetup/blob/master/media/0.supermeetup_design.mp4)

Here's the wireframing file: [Fluid UI](https://www.fluidui.com/editor/live/preview/cF9Ha29HS0NpRExLYkI5VG9wVGhLdW9Ka3VaZmtYVGJweQ==)

<img src='https://github.com/super-meetup/SuperMeetup/blob/master/media/0.supermeetup_design.gif' title='GIF Walkthrough SuperMeetup Design ' width='' alt='GIF Walkthrough SuperMeetup Design' />


GIF created with [EZGIF.com](https://ezgif.com/video-to-gif).

## Sprint 1

Here's a walkthrough of finished user stories:
[video](https://github.com/super-meetup/SuperMeetup/blob/master/media/sprint1/sprint1.mp4)

<img src='https://github.com/super-meetup/SuperMeetup/blob/master/media/sprint1/sprint1.gif' title='GIF Walkthrough SuperMeetup Sprint 1' width='' alt='GIF Walkthrough SuperMeetup Sprint 1' />


GIF created with [EZGIF.com](https://ezgif.com/video-to-gif).

## Sprint 2

Here's a walkthrough of finished user stories:
[video](https://github.com/super-meetup/SuperMeetup/blob/master/media/sprint2/sprint2.mp4)

Part I

<img src='https://github.com/super-meetup/SuperMeetup/blob/master/media/sprint2/part1.gif' title='GIF Walkthrough SuperMeetup Sprint 2 part 1' width='' alt='GIF Walkthrough SuperMeetup Sprint 2 part 1' />

Part II 

<img src='https://github.com/super-meetup/SuperMeetup/blob/master/media/sprint2/part2.gif' title='GIF Walkthrough SuperMeetup Sprint 2 part 2' width='' alt='GIF Walkthrough SuperMeetup Sprint 2 part 2' />

PartIII

<img src='https://github.com/super-meetup/SuperMeetup/blob/master/media/sprint2/part3.gif' title='GIF Walkthrough SuperMeetup Sprint 2 part 3' width='' alt='GIF Walkthrough SuperMeetup Sprint 2 part 3' />

## Sprint 3

Here's a walkthrough of finished user stories:
[video](https://github.com/super-meetup/SuperMeetup/blob/master/media/sprint3/sprint3.mp4)

Part I

<img src='https://github.com/super-meetup/SuperMeetup/blob/master/media/sprint3/part1.gif' title='GIF Walkthrough SuperMeetup Sprint 3 part 1' width='' alt='GIF Walkthrough SuperMeetup Sprint 3 part 1' />

Part II 

<img src='https://github.com/super-meetup/SuperMeetup/blob/master/media/sprint3/part2.gif' title='GIF Walkthrough SuperMeetup Sprint 3 part 2' width='' alt='GIF Walkthrough SuperMeetup Sprint 3 part 2' />

PartIII

<img src='https://github.com/super-meetup/SuperMeetup/blob/master/media/sprint3/part3.gif' title='GIF Walkthrough SuperMeetup Sprint 3 part 3' width='' alt='GIF Walkthrough SuperMeetup Sprint 3 part 3' />


GIF created with [EZGIF.com](https://ezgif.com/video-to-gif).

## Open-source libraries used

- [CodePath OAuth Handler](https://github.com/codepath/android-oauth-handler) - Android OAuth Wrapper makes authenticating with APIs simple
- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [Parceler library](http://guides.codepath.com/android/Using-Parceler)
- [Retrofit](https://github.com/square/retrofit)
- [Gson](https://github.com/google/gson)

## License

Copyright 2017 [SuperMeetup](https://github.com/super-meetup)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
