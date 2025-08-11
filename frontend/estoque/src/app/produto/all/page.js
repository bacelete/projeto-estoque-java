'use client'
import { useEffect, useState } from "react";
import { useRouter } from 'next/navigation'

export default function AllProducts() {
    const [authenticated, setAuthentication] = useState(false);
    const [listaProdutos, setListaProdutos] = useState([]);
    const router = useRouter();


    useEffect(() => {
        const token = localStorage.getItem('token');

        if (token != null) {
            setAuthentication(true);
        }
        else {
            setTimeout(() => {
                router.push('/');
            }, 2000)
        }

        const fetchProdutos = async () => {
            try {
                const response = await fetch('http://localhost:8080/produto/all', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
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