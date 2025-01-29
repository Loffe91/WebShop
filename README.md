# Projektbeskrivning :briefcase:
Backend för E-handelsbutik

## Översikt :clipboard:
Utveckla en backend-struktur för en e-handelsbutik i Java. Systemet ska hantera
produkter, kunder och ordrar genom en trelagers-arkitektur med tydlig
separation mellan datahämtning, affärslogik och användarinteraktion.

## Arkitektur :triangular_ruler:
Applikationen ska struktureras i tre huvudsakliga lager:
* Repository-lager för databasinteraktion via JDBC
* Service-lager som hanterar affärslogik
* Controller-lager med konsolbaserat användargränssnitt

## Huvudfunktioner :gear:

### Produkthantering :package:
* Lista alla tillgängliga produkter
* Sökfunktion för produkter
* Filtrera produkter baserat på olika kriterier

### Kundhantering :telephone_receiver:
* Registrera nya kunder
* Uppdatera befintlig kundinformation

### Orderhantering :mailbox:
* Skapa nya ordrar
* Visa orderhistorik för kunder

### Lagerhantering :package:
* Uppdatera lagersaldo för produkter
* Kontrollera lagerstatus

### Ekonomihantering :money_with_wings:
* Prissättning och prishistorik
* Rabatthantering
* Fakturering
* Betalningshantering

### Validering och Felhantering :bulb:
* Inmatningsvalidering för alla formulär
* Strukturerad felhantering och loggning
* Felmeddelanden till användare
