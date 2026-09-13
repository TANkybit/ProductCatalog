# Product Catalog App

A simple Android Product Catalog application developed as part of the Neurogine Junior Mobile Developer technical assessment.

## Tech Stack

- Kotlin
- Android Studio
- Retrofit for API communication
- Gson for JSON parsing
- Coil for image loading
- RecyclerView for displaying products
- ViewModel and StateFlow for UI state management
- SwipeRefreshLayout for pull-to-refresh

## API

The application uses the free DummyJSON API:

https://dummyjson.com/products

The following API endpoints are used:

- Product list and pagination:
  `GET /products?limit=20&skip=0`

- Product detail:
  `GET /products/{id}`

- Product search:
  `GET /products/search?q={query}`

## Features

### Product List
- Displays product thumbnail, title, and price.
- Loads the first 20 products when the application starts.

### Pagination
- Automatically loads more products when the user scrolls near the bottom.
- Uses the `skip` parameter provided by the API.

### Product Search
- Provides a search box for finding products.
- Uses the DummyJSON search endpoint.
- Search requests are debounced by 500ms to avoid sending a request for every keystroke.

### Product Details
- Users can tap a product to open its detail screen.
- Displays:
  - Product image
  - Product title
  - Price
  - Rating
  - Full description

### UI States
The application handles:
- Loading
- Success
- Empty results
- Error with retry button

### Pull-to-Refresh
- Users can swipe down on the product list to refresh the products.

### Image Handling
- Displays a placeholder while product images are loading.
- Displays a fallback image when an image fails to load.

## Architecture

The application separates responsibilities into different layers.

### Data Layer
Responsible for:
- API communication
- Product data models
- Repository

### UI Layer
Responsible for:
- Activities
- RecyclerView adapter
- Product display

### ViewModel Layer
The ViewModel manages:
- UI state
- Product loading
- Pagination
- Search
- Communication with the repository

StateFlow is used to expose UI state from the ViewModel to the Activity.

## How to Run

1. Clone this repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and download the required dependencies.
4. Connect an Android device or start an Android emulator.
5. Run the application.

An internet connection is required because the application retrieves product data from DummyJSON.

## AI Assistance

AI tools were used during development for guidance, debugging assistance, and explanations of Android/Kotlin concepts.

The implementation was reviewed and tested during development, and I am able to explain the code and architectural decisions used in the project.

## Unfinished / Future Improvements

The current implementation focuses on the requirements of the technical assessment.

Possible future improvements include:
- Unit tests
- Improved product image gallery
- More detailed product information
- Improved UI animations
- Better offline handling
