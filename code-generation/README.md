# code-generation

### TODO
- dogovoriti se za podjelu poslova
- dogovoriti rok do kojeg treba biti gotovo (predlazem 17.1. ~tyr)

### Napomene:
Produkcije za codegen odgovaraju poretku kojim su navedene u *bnf.txt* datoteci ili alternativno pogledajte u *Productions.java*.
Ni u kojem slucaju ne gledati listu produkcija koja se nalazi u komentaru na vrhu manipulatora jer na nekim mjestima je izmjesan redosljed.

Grananje unutar funkcije za generiranje koda, napravljeno je switchevima, ekstremno je pozeljno da tako i ostane.
Ukoliko je za 2 (ili vise) produkcija identican kod koji treba generirati koristite propadanje kroz caseove (ili refactorajte u posebnu metodu), ali ova druga opcija je bolja jer smanjuje kolicinu skrolanja.
