Se7en SE Project
===
- **Slack:** team-se7en
- **URL:** http://se-team-se7en.appspot.com/#table

Installation
---
- Get the vagrant box from GDrive (newest version: v6)
- Install **Vagrant** and **Virtualbox 5+**
- Clone this repo
- vagrant box add SOMEPATH/se7en-v6.box --name se7en-v6
- vagrant up

Exporting new version of the VM
---
- vagrant halt
- vagrant package --output se7en-v7.box
- Upload the new .box to the Google Drive folder

Connecting to the database
---
- **IP:** 173.194.250.0
- **User:** se7en
- **Password:** k1vttuIYXqOPe5!
- **Database:** se7en
