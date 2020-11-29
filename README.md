#Speedrunners vs Viewers plugin 1.16.x

#Getting Started

1. `git clone https://https://github.com/etztrefis/SpeedrunnerVsViewers.git`
2. add `spigot 1.16.x` based core to the dependencies of project
3. build artifacts 
4. add built plugin into /plugins folder of your server

#Notes
 - Make sure u change `sender.getName().equals("_PWGood_") || sender.getName().equals("trefis")`
 in **commands/SpeedrunnerCommand.java:35** to your nickname for give access to player to add other players to
 speedrunners team.
 #Usage
 ######Commands
 Use command `/speedrunner <username> [remove]` to add a player to speedrunners team.
 
 Use command `/grouplist` to view active roles for all players.
 
 #Will be added in the future
 
 - Separate config for access to **/speedrunner** command.
 
 ![Okayeg](https://cdn.betterttv.net/emote/5de9cb6191129e77b47ca987/1x) ...