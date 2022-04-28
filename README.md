# Job Advisor

Job Advisor is an employment-oriented application whose core purpose is to provide a platform not only to job seekers to search for job offers, browse through lists of employers and job offers, and save the posts they like, but also to employers who post new job offers and meanwhile search for relevant job seekers. 

*NOTE* : This readme is a brief description of the project. The full documentation is available in the repository.

# Introduction

The core functions of a job seeker include searching for job offers with respect to a few parameters, saving job offers they are interested in and browsing them later, searching for an employer, following an employer and browsing through list of recommended job offers and recommended employers. On the other hand, an employer can mainly perform tasks such as publishing a new job offer and browsing through list of its already published job offers, browsing through the list of its followers and searching for job seekers matching a certain skill. Other trivial functions of both job seekers and employers are updating account details like password and deleting account. The third user of the application is an admin user whose core purpose is to manage and perform analysis of the application. The admin can delete Job offers and users (both job seekers and employers). Moreover, this user can find the most common skills found in job seekers, browse through a list of most followed employers and look for the cities with the greatest number of job offers.

# ARCHITECTURE

This application is a client-server application. On the client side, a graphical user interface based on JavaFx allows users to interact with the application. Moreover, two Java drivers has been used to bridge data between the GUI and the server side: one driver for handling MongoDB connections and one for handling Neo4J connections. On the server side, there is a document database and a graph database.
The document database is a MongoDB replica set and the graph database is a Neo4J replica set. The MongoDB replica set consists of one primary and two secondary replicas to ensure eventual consistency. The Neo4J replica set consists of three cores and three replicas to ensure eventual consistency.

