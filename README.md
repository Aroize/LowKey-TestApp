### Pexels-Demo-App

#### Implemented
- Feed page
  - by design : corners, shadows, cells
  - image caching via Glide
  - data caching via custom implementation
  - simple image thumbnails
  - pull to refresh
- Details page
  - data caching via custom implementation
  - simple image thumbnails

#### Project structure
- app : Android-app entrypoint
- core : all core libraries
  - arch : module with base classes for implementing features
  - coroutines : dispatchers wrapper for future usage in tests
  - db : daos for working with persistence database
  - di : base module to implement di in features
  - navigation : conventional approach for navigation in project. Quite simple, but OK for demo-app
  - network : base module for encapsulate work with network layer
  - recycler : implementation of composite adapter + diff util integration
  - utils : base utils module
- details : feed post details feature
  - api : lightweight module with entrypoint to feature, can be used in other feature-modules
  - impl : feature implementation, must be used only in `app`
- posts-domain : data\domain layer for feed feature. Due to size of project, decided not to do a complete fizz-buzz-enterprise solution and merged two layers in one module

#### Time spent on the project
12h:39m:11s