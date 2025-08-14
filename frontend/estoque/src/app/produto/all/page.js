'use client'
import { useEffect, useState } from "react";
import { useRouter } from 'next/navigation'

export default function AllProducts() {
    const [listaProdutos, setListaProdutos] = useState([]);

    useEffect(() => {
        const fetchProdutos = async () => {
            try {
                const response = await fetch('http://localhost:8080/produto/all', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include'
                })
                const lista = await response.json();
                console.log(lista);
                setListaProdutos(lista);
            }
            catch (e) {
                console.log('Erro ao buscar produtos:', e);
            }
        }
        fetchProdutos();
    }, []);

    return (
        <>
            <ul className="text-black p-2">
                {listaProdutos.map((produto) => (
                    <li key={produto.id}>{produto.nome}</li>
                ))}
            </ul>
        </>
    )

}