# Documentation du TP - Séquence 4

## Architecture Globale du Projet
Ce TP met en œuvre une chaîne de déploiement automatisée combinant Packer, Docker, K3d et Ansible dans un environnement GitHub Codespaces.

### Séquence 3 : Automatisation et Personnalisation Applicative

#### 1. Choix de l'application
Pour valider le déploiement, nous avons utilisé l'application web statique **Hextris** présente dans le dépôt.

#### 2. Construction de l'image avec Packer (`nginx.pkr.hcl`)
Nous avons configuré Packer pour :
* Récupérer l'image officielle de base `nginx:latest`.
* Utiliser un provisioner de type `file` pour injecter le fichier `index.html` du jeu Hextris dans le répertoire de diffusion de Nginx (`/usr/share/nginx/html/index.html`).
* Exposer le port 80 et configurer la commande de démarrage par défaut.
* Taguer l'image finale sous le nom `my-nginx-custom:v1`.

#### 3. Importation dans le Cluster K3d
Le cluster K3d fonctionnant de manière isolée sans accès direct à un registre Docker distant, nous avons importé manuellement l'image construite par Packer dans l'environnement d'exécution du cluster avec la commande :
```bash
k3d image import my-nginx-custom:v1 -c lab
