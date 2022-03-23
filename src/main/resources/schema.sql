    
    CREATE TABLE "planets" (
        "PlanetId" INT PRIMARY KEY NOT NULL,
        "NameofPlanet" VARCHAR NOT NULL,
        "Mass" VARCHAR NOT NULL,
        "Moons" INT NOT NULL,
        "Distance" BIGINT NOT NULL
    );

    CREATE TABLE "dwarfplanets" (
        "dwarfId" INT PRIMARY KEY NOT NULL,
        "NameofDwarf" VARCHAR NOT NULL,
        "Mass" VARCHAR NOT NULL,
        "Moons" INT NOT NULL,
        "Distance" BIGINT NOT NULL

    );

    INSERT INTO "planets" VALUES (1, 'Mercury', '3.285 × 10^23 kg', 0, 36000000);
    INSERT INTO "planets" VALUES (2, 'Venus', '4.867 × 10^24 kg', 0, 67200000);
    INSERT INTO "planets" VALUES(3, 'Earth', 'M🜨 = 5.9722×1024 kg', 1, 93000000);
    INSERT INTO "planets" VALUES(4, 'Mars', '6.39 × 10^23 kg', 2, 142000000);
    INSERT INTO "planets" VALUES(5, 'Jupiter', '1.898 × 10^27 kg', 79, 484000000);
    INSERT INTO "planets" VALUES(6, 'Saturn', '5.683 × 10^26 kg', 82, 886000000);
    INSERT INTO "planets" VALUES(7, 'Uranus', '8.681 × 10^25 kg', 27, 1800000000);
    INSERT INTO "planets" VALUES(8, 'Neptune', '1.024 × 10^26 kg', 14, 2800000000);

    INSERT INTO "dwarfplanets" VALUES (1, 'Pluto', '1.30900 × 1022 kilograms', 5, 3700000000);
    INSERT INTO "dwarfplanets" VALUES (2, 'Ceres', '9.445×1020 kg', 0, 257000000);
    INSERT INTO "dwarfplanets" VALUES (3, 'Makemake', '≈3.1×1021 kg', 1, 4253000000);
    INSERT INTO "dwarfplanets" VALUES (4, 'Haumea', '(4.006±0.040)×1021 kg', 2, 4010000000);
    INSERT INTO "dwarfplanets" VALUES (5, 'Eris', '1.672 × 10^22 kg', 1, 6289000000);