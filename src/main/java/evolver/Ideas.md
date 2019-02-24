


#Rubrik nivå 1

##Att göra
* Globala inställningar: view-port
* Skriva unit-tester
* GUI
* Kolla min svarta moleskine efter anteckningar
* Kolla min gamla javascript-implementation
* Kolla github-gist efter anteckningar
* checka in koden på github
* skriva unit-tester
* hantering för att undvika komplexa polygoner
* kolla upp att använda java awt polygon
* Klona en candidate
* ta bort en viss Trait
* lägg till en Trait
* Byt ordning på Traits (shuffle)
* ta bort Traits som inte bidrar (osynliga, dolda eller noll area) 
* skriva en factory för att skapa Candidates
* sätta clip på det område som ritas?
* hantera andra bildformat än png
* undersöka vilken image-type som är lämpligast i BufferedImage
* se om det går att rita effektivare
* Göra möjligt att importera olika bildformat, inte bara png
* göra möjligt att lätt öppna från Mac/Linux/Windows
* installera CheckStyle plugin på linux-kärran
* skriva unit-tester för alla Traits (kolla muteringen på alla delar, och återställningen)
* skriva toString() för alla Traits
* kolla rendering hints (tex för antialiasing)
  https://docs.oracle.com/javase/7/docs/api/java/awt/RenderingHints.html
* bakgrunden borde vara ogenomskinlig
* det finns ju andra metoder för att rita polygoner, som kanske är effektivare?

##simulated annealing
Tillåta ett visst fel
Lista ut “avsvalningskurva” genom att jämföra simuleringar med olika kurva
Spara ett snapshot
Lista med info om respektive trait
Möjlighet att påverka ett trait (editera, radera) i den listan.

##genetic algorithm
Många parallella samtidiga muteringar

##View-port med andra vyer
* wireframe
* edit-mode
* diff
* sepia
* svartvit
* mutate (default)

styra hur mycket/starkt det muteras

editera shapes

regel: punkter som slumpas är inom "ramen"

Factory metod för att skapa Candidates

andra shapes
class CircleShape implements Trait
fylldFärg, kantlinjefärg, kantlinjeBredd, position och radie

Class EllipsShape implements Trait
Class Line implements Trait

First Header | Second Header
------------ | -------------
Content from cell 1 | Content from cell 2
Content in the first column | Content in the second column