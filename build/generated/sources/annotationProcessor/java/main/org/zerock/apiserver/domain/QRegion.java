package org.zerock.apiserver.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegion is a Querydsl query type for Region
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegion extends EntityPathBase<Region> {

    private static final long serialVersionUID = -1388376423L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegion region = new QRegion("region");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QRegion parentRegion;

    public QRegion(String variable) {
        this(Region.class, forVariable(variable), INITS);
    }

    public QRegion(Path<? extends Region> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegion(PathMetadata metadata, PathInits inits) {
        this(Region.class, metadata, inits);
    }

    public QRegion(Class<? extends Region> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentRegion = inits.isInitialized("parentRegion") ? new QRegion(forProperty("parentRegion"), inits.get("parentRegion")) : null;
    }

}

