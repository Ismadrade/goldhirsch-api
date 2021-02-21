package br.com.goldhirsch.adapter;

public interface Adapter<Entity, Response, Request> {
    Response toResponse(Entity model);
    Entity ToEntity(Request request);
}
