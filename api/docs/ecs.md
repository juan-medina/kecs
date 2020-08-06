## Entities, components and systems

Entity–component–system (ECS) is an architectural patter that follows the composition over inheritance principle that allows greater flexibility in defining entities
where every object in a world.

Every entity consists of one or more components which contains data or state. Therefore, the behavior of an entity can be changed at runtime by systems that add, remove or mutate components.

This eliminates the ambiguity problems of deep and wide inheritance hierarchies that are difficult to understand, maintain and extend.

Common ECS approaches are highly compatible and often combined with data-oriented design techniques.

For a more in deep read on this topic I could recommend [this article](https://medium.com/ingeniouslysimple/entities-components-and-systems-89c31464240d){target=_blank}.

## Data-oriented Design

A data oriented design is a design in which the logic of the application is built up of data sets, instead of procedural algorithms,
they are highly optimized, specially because how computer organize data.

For a more in deep read on this topic I could recommend [this article](http://gamesfromwithin.com/data-oriented-design){target=_blank}.


## Understanding Data-oriented design for ECS
On [GDC](https://gdconf.com/){target=_blank} 2019 [Unity](https://unity.com/){target=_blank} make a fascinating video about data-oriented design and ECS,
and how this has a huge impact on performance, I strongly recommend watching this video.

<div class="video-wrapper">
  <iframe width="1280" height="720" src="https://www.youtube.com/embed/0_Byw9UMn9g" frameborder="0" allowfullscreen></iframe>
</div>
