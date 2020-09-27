package me.micrusa.githubstats;

import io.realm.DynamicRealm;
import io.realm.RealmSchema;

public class RealmMigration implements io.realm.RealmMigration {
    DynamicRealm realm;
    RealmSchema schema;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        this.realm = realm;
        this.schema = realm.getSchema();

        if(oldVersion <= 2){
            migrateTo3();
        }
    }

    private void migrateTo3(){
        schema.get("Repo")
                .removeField("cachedResponse")
                .removeField("latestCache");

        schema.get("User")
                .removeField("cachedResponse")
                .removeField("latestCache");
    }
}
