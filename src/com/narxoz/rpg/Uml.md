Classes & Interfaces:

ActionCommand (interface)
+execute() : void
+undo() : void
+getDescription() : String
AttackCommand (implements ActionCommand)
-target : ArenaOpponent
-attackPower : int
-damageDealt : int
+execute()
+undo()
+getDescription()
HealCommand (implements ActionCommand)
-target : ArenaFighter
-healAmount : int
-actualHealApplied : int
+execute()
+undo()
+getDescription()
DefendCommand (implements ActionCommand)
-target : ArenaFighter
-dodgeBoost : double
+execute()
+undo()
+getDescription()
ActionQueue
-queue : List<ActionCommand>
+enqueue(ActionCommand)
+undoLast()
+executeAll()
+getCommandDescriptions()

Receivers:

ArenaFighter
ArenaOpponent

Relationships:

ActionQueue uses ActionCommand
Each concrete command operates on a receiver (ArenaFighter or ArenaOpponent)

            +----------------+
            |  ActionCommand |
            +----------------+
            | +execute()     |
            | +undo()        |
            | +getDescription()|
            +----------------+
                    ^
        +-----------+-----------+
        |           |           |
+----------------+ +-------------+ +----------------+
| AttackCommand  | | HealCommand | | DefendCommand  |
+----------------+ +-------------+ +----------------+
| -target        | | -target     | | -target        |
| -attackPower   | | -healAmount | | -dodgeBoost    |
| -damageDealt   | | -actualHeal | +----------------+
+----------------+ +-------------+
| +execute()     | | +execute()  |
| +undo()        | | +undo()     |
| +getDesc()     | | +getDesc()  |
+----------------+ +-------------+

             +----------------+
             |  ActionQueue   |
             +----------------+
             | -queue: List   |
             +----------------+
             | +enqueue()     |
             | +undoLast()    |
             | +executeAll()  |
             | +getDescList() |
             +----------------+




Classes:

DefenseHandler (abstract)
-next : DefenseHandler
+setNext(DefenseHandler) : DefenseHandler
+handle(int, ArenaFighter) : void (abstract)
#passToNext(int, ArenaFighter) : void
DodgeHandler (extends DefenseHandler)
-dodgeChance : double
-random : Random
+handle(int, ArenaFighter)
BlockHandler (extends DefenseHandler)
-blockPercent : double
+handle(int, ArenaFighter)
ArmorHandler (extends DefenseHandler)
-armorValue : int
+handle(int, ArenaFighter)
HpHandler (extends DefenseHandler)
+handle(int, ArenaFighter)
          +------------------+
          |  DefenseHandler  |
          +------------------+
          | -next: DefenseHandler |
          +------------------+
          | +setNext()       |
          | +handle()        |
          | #passToNext()    |
          +------------------+
                    ^
        +-----------+------------+-----------+----------+
        |           |            |           |
+----------------+ +--------------+ +------------+ +---------+
| DodgeHandler   | | BlockHandler | | ArmorHandler| | HpHandler|
+----------------+ +--------------+ +------------+ +---------+
| -dodgeChance   | | -blockPercent| | -armorValue| |         |
| -random        | |              | |            | |         |
+----------------+ +--------------+ +------------+ +---------+
| +handle()      | | +handle()    | | +handle()  | | +handle()|
+----------------+ +--------------+ +------------+ +---------+