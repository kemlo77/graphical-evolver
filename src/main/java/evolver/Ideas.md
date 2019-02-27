


# Rubrik nivå 1

## Att göra
* lägga till en licens?
* Globala inställningar: view-port
* GUI
* Kolla min svarta moleskine efter anteckningar
* Kolla min gamla javascript-implementation
* Kolla github-gist efter anteckningar (polygon-imatningnen i HTML)
* hantering för att undvika komplexa polygoner

# Prestanda
* undersöka vilken image-type som är lämpligast i BufferedImage
* se om det går att rita effektivare
* kolla upp att använda java awt polygon
* det finns ju andra metoder för att rita polygoner, som kanske är effektivare?

# Saknad funktionalitet
* Göra möjligt att importera olika bildformat, inte bara png
* göra möjligt att lätt öppna från Mac/Linux/Windows
* spara till svg, importera från svg
* ta bort en viss Trait
* lägg till en Trait
* Byt ordning på Traits (shuffle)
* ta bort Traits som inte bidrar (osynliga, dolda eller noll area)
* beräkna area av cirkel, polygon (ej komplex). För att kunna ta bort dom som har ~0 i area
* Tillåta "genomskinlighet" är noll för att element ska försvinna. Eller ha ett värde skiljt från noll för att tvinga dom att evolvera...
* Ändra hur många element som muteras beroende på degree
  Ex: över 75% alla färger + ett shape muteras
  under 25% så muteras bara en färg eller transparans
  alternativt bara ett shape
* Ändra maxvärde för LineWidth och CircleDiameter




# Hämta in kunskap
* rotera en ellips?
* uml-diagram, klass-diagram
* kolla rendering hints (tex för antialiasing)
  https://docs.oracle.com/javase/7/docs/api/java/awt/RenderingHints.html
* Klona en candidate
* skriva en factory för att skapa Candidates
* UML-diagram / class-diagram
* sätta clip på det område som ritas?

# Unit tester
* skriva unit-tester för alla Traits (kolla muteringen på alla delar, och återställningen)

# Done
* skriva toString() för alla Traits
* checka in koden på github
* styra hur mycket/starkt det muteras
* regel: punkter som slumpas är inom "ramen"
* kolla om det blev effektivare utan att skapa en ny Point varje gång? (Kanske lite.)
* Kan man göra detta utan att skapa en ny Color varje gång. (Nej)
* tillåta att degree (float)(byt namn?) är ==0. Mutationen som genereras kommer ändå inte att vara noll. (testa noga). Dvs alltid en viss mutation.
* installera CheckStyle plugin på linux-kärran

# Ideer
* Lista med info om respektive trait
* Factory metod för att skapa Candidates
* bakgrunden borde vara ogenomskinlig


## simulated annealing
Tillåta ett visst fel
Lista ut “avsvalningskurva” genom att jämföra simuleringar med olika kurva
Spara ett snapshot
Möjlighet att påverka ett trait (editera, radera) i den listan.

## Genetic algorithm
Många parallella samtidiga muteringar

## View-port med andra vyer
* wireframe
* edit-mode
* diff
* sepia
* svartvit
* mutate (default)