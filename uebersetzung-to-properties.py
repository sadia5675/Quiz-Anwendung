import os

with open("uebersetzungen.csv", "r") as file:
    sprachen = next(file).strip().split(';')[1:]

    for sprache in sprachen:
        properties_file = f'messages_{sprache}.properties'

        

        with open(os.path.join('src', 'main', 'resources', properties_file), 'w') as prop_file:
            
            for satz in file:
                    satz = satz.strip().split(';')
                    prop_key = satz[0]
                    prop_val = satz[sprachen.index(sprache)+1]
                    prop_file.write(f"{prop_key}={prop_val}\n")
                    
            file.seek(0)
            next(file)
            
        

