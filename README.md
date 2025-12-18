# Movie Recommendation System

A Java-based console application that recommends movies based on user ratings
and content similarity.

## Overview
This project implements a simple movie recommendation system that allows users
to rate movies, view a movie catalog, and receive personalized recommendations.
Movie and rating data are loaded from text files, and recommendations are generated
using a content-based filtering approach.

## Features
- Load and parse movie data from files
- Allow users to rate movies and persist ratings locally
- Handle cold-start scenarios by recommending popular movies
- Generate personalized recommendations based on genre and director similarity

## Recommendation Logic
- Cold Start: If the user has no ratings, recommend top-rated movies
- Content-Based Filtering:
  - Identify movies rated 4–5 stars by the user
  - Recommend unrated movies with similar genres
  - Add additional weight for matching directors

## Project Structure
src/
├── Main.java # Program entry point and user interaction
├── Movie.java # Movie data model
├── MovieCatalog.java # Movie data loading and management
├── RatingManager.java # User rating persistence and validation
└── RecommendationEngine.java # Recommendation logic

## Technologies
- Java
- File I/O
- Object-Oriented Programming

## Notes
This project was developed with team members as part of a software development
course and demonstrates basic software engineering practices such as modular
design, error handling, and separation of concerns.
