package jat.imview.rest.restMethod;

import jat.imview.rest.Response;

/**
 * Created by bulat on 23.12.15.
 */
public interface RestMethod<T> {
    public Response execute();
}
