package com.globalsync.eliteperformance.util;

import java.util.List;

public record PageResponse<T>(List<T> items, int count) {
}
