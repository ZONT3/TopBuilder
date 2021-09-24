package ru.zont.topbuilder.core.implement;

import ru.zont.topbuilder.core.DecisionEntry;

public class RatingDecisionEntry<T> extends DecisionEntry<T> {
    public RatingDecisionEntry(T lhs,
                               T rhs,
                               int decision,
                               float ratingDeltaLhs,
                               float ratingDeltaRhs,
                               float ratingCurrentLhs,
                               float ratingCurrentRhs) {
        super(lhs, rhs, decision);
        data.put("ratingDeltaLhs", ratingDeltaLhs);
        data.put("ratingDeltaRhs", ratingDeltaRhs);
        data.put("ratingCurrentLhs", ratingCurrentLhs);
        data.put("ratingCurrentRhs", ratingCurrentRhs);
    }

    public float getRatingDeltaLhs() {
        return get("ratingDeltaLhs");
    }

    public float getRatingDeltaRhs() {
        return get("ratingDeltaRhs");
    }

    public float getRatingCurrentLhs() {
        return get("ratingCurrentLhs");
    }

    public float getRatingCurrentRhs() {
        return get("ratingCurrentRhs");
    }
}
